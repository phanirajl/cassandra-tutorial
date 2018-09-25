package com.dataart.cassandra.cassandracourse.service;

import com.dataart.cassandra.cassandracourse.domain.Booking;
import com.dataart.cassandra.cassandracourse.domain.BookingDetail;
import com.dataart.cassandra.cassandracourse.domain.BookingPrimaryKey;
import com.dataart.cassandra.cassandracourse.domain.Guest;
import com.dataart.cassandra.cassandracourse.domain.Hotel;
import com.dataart.cassandra.cassandracourse.domain.Room;
import com.dataart.cassandra.cassandracourse.exception.ApplicationLogicException;
import com.dataart.cassandra.cassandracourse.repository.BookingDetailRepository;
import com.dataart.cassandra.cassandracourse.repository.BookingsRepository;
import com.dataart.cassandra.cassandracourse.repository.GuestRepository;
import com.dataart.cassandra.cassandracourse.repository.HotelsRepository;
import com.dataart.cassandra.cassandracourse.repository.RoomsRepository;
import com.dataart.cassandra.cassandracourse.web.request.FreeRoomRequest;
import java.time.LocalDate;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 *
 * @author alitvinov
 */
@Component
@DependsOn(value = "dataInitializer")
@Slf4j
public class BookingService {

    private final static BookingDetail BOOKING_DETAIL_STUB = new BookingDetail();

    @Autowired
    private HotelsRepository hotelsRepository;

    @Autowired
    private RoomsRepository roomsRepository;

    @Autowired
    private GuestRepository guestRepository;

    @Autowired
    private BookingsRepository bookingRepository;

    @Autowired
    private CassandraOperationHelper helper;

    @Autowired
    private BookingDetailRepository bookingDetailsRepository;

    public Mono<Booking> addBooking(Booking b) {

        BookingPrimaryKey pk = b.getKey();

        Mono<Hotel> hotelMono = hotelsRepository
                .findById(pk.getHotelId())
                .switchIfEmpty(
                        getDeferWithExceptionForEmptyMono(String.format("Hotel %s not exists", pk.getHotelId()))
                );

        Mono<Room> roomMono = roomsRepository
                .getRoom(pk.getHotelId(), pk.getRoom())
                .switchIfEmpty(
                        getDeferWithExceptionForEmptyMono(String.format("Room %d does not exists for hotel %s.", pk.getRoom(), pk.getHotelId()))
                );

        Mono<Guest> guestMono = guestRepository
                .findById(b.getGuest())
                .switchIfEmpty(
                        getDeferWithExceptionForEmptyMono(String.format("Guest %s does not exists.", b.getGuest()))
                );

        Flux<BookingDetail> bookingMono = bookingDetailsRepository
                .getCreatedBookingDetails(pk.getHotelId(), pk.getStartDate(), pk.getEndDate())
                .switchIfEmpty(Flux.defer(() -> {
                    return Flux.just(BOOKING_DETAIL_STUB);
                }));

        Flux<BookingDetail> bookingDetails = hotelMono
                .then(roomMono)
                .then(guestMono)
                .thenMany(bookingMono);

        Mono<Booking> result = bookingDetails
                .collect(Collectors.toSet())
                .flatMap(set -> {
                    log.info("Current bookings " + Arrays.toString(set.toArray()));
                    Set<Integer> occupiedRooms = extractOcupiedRoomsFromBookingDetail(set);
                    boolean bookingExists = occupiedRooms.contains(pk.getRoom());
                    if (bookingExists) {
                        throw new ApplicationLogicException("Already exists booking for provided hotel, room and dates.");
                    }
                    return helper.insertIfNotExists(b);
                });

        return result.flatMap(ab -> {
            //wait until data in details table will be saved
            saveDataToBookingDetailsTable(ab).subscribe(bd -> {
                log.info("Booking detail successfully stored {}", bd);
            });
            return Mono.just(ab);
        });
    }

    public Flux<Room> getFreeRooms(FreeRoomRequest request) {
        //Get existed bookings detail for specified period
        Flux<BookingDetail> lst = bookingDetailsRepository
                .getCreatedBookingDetails(
                        request.getHotel(),
                        request.getStartDate(),
                        request.getEndDate()
                );
        Mono<Set<BookingDetail>> bookingDetails = lst.collect(Collectors.toSet());
        //get all rooms for hotel which are not present in existed bookings set
        Flux<Room> freeRooms = bookingDetails.flatMapMany(set -> {
            Set<Integer> occupiedRooms;
            if (set == null) {
                return roomsRepository.getRoomsForHotel(request.getHotel());
            } else {
                occupiedRooms = extractOcupiedRoomsFromBookingDetail(set);
                return roomsRepository.getRoomsForHotel(request.getHotel()).filter(
                        r -> {
                            return !occupiedRooms.contains(r.getRoomNumber());
                        });
            }
        });
        return freeRooms;
    }

    public Flux<Booking> getBookedRooms(FreeRoomRequest request) {
        //Get existed bookings detail for specified period
        Flux<BookingDetail> lst = bookingDetailsRepository
                .getCreatedBookingDetails(
                        request.getHotel(),
                        request.getStartDate(),
                        request.getEndDate()
                );
        Mono<Set<BookingDetail>> bookingDetails = lst.collect(Collectors.toSet());
        //get all bookings
        Flux<Booking> result = bookingDetails.flatMapMany(s -> {
            Set<BookingPrimaryKey> ss = new HashSet<>();
            s.forEach(bd -> {
                ss.add(new BookingPrimaryKey(bd.getHotelId(), bd.getStartDate(), bd.getEndDate(), bd.getRoom()));
            });
            return bookingRepository.findAllById(ss);
        });
        return result;
    }

    private Set<Integer> extractOcupiedRoomsFromBookingDetail(Set<BookingDetail> details) {
        Set<Integer> occupiedRooms;
        if (details == null) {
            occupiedRooms = Collections.<Integer>emptySet();
        } else {
            occupiedRooms = details.stream().map(BookingDetail::getRoom).collect(Collectors.toSet());
        }
        return occupiedRooms;
    }

    private Flux<BookingDetail> saveDataToBookingDetailsTable(Booking b) {
        return bookingDetailsRepository.saveAll(createBookingDetailFromBooking(b));
    }

    private Date convertToDateViaInstant(LocalDate dateToConvert) {
        return Date.from(dateToConvert.atStartOfDay()
                .atZone(ZoneId.systemDefault())
                .toInstant());
    }

    private List<BookingDetail> createBookingDetailFromBooking(Booking b) {
        BookingPrimaryKey pk = b.getKey();
        List<BookingDetail> resList = new LinkedList<>();
        LocalDateTime ldStart = LocalDateTime.ofInstant(
                pk.getStartDate().toInstant(),
                ZoneId.systemDefault());
        LocalDateTime ldEnd = LocalDateTime.ofInstant(
                pk.getEndDate().toInstant(),
                ZoneId.systemDefault());
        while (ldStart.compareTo(ldEnd) <= 0) {
            resList.add(new BookingDetail(pk.getHotelId(), convertToDateViaInstant(ldStart.toLocalDate()), pk.getRoom(), pk.getStartDate(), pk.getEndDate()));
            ldStart = ldStart.plusDays(1);
        }
        return resList;
    }

    private <T> Mono<T> getDeferWithExceptionForEmptyMono(String exceptionMessage) {
        return Mono.defer(() -> {
            throw new ApplicationLogicException(exceptionMessage);
        });
    }
}
