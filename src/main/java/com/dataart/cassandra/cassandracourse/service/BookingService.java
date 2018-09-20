package com.dataart.cassandra.cassandracourse.service;

import com.dataart.cassandra.cassandracourse.domain.Booking;
import com.dataart.cassandra.cassandracourse.domain.BookingDetail;
import com.dataart.cassandra.cassandracourse.domain.Guest;
import com.dataart.cassandra.cassandracourse.domain.Room;
import com.dataart.cassandra.cassandracourse.exception.ApplicationLogicException;
import com.dataart.cassandra.cassandracourse.repository.BookingDetailRepository;
import com.dataart.cassandra.cassandracourse.repository.BookingsRepository;
import com.dataart.cassandra.cassandracourse.repository.GuestRepository;
import com.dataart.cassandra.cassandracourse.repository.HotelsRepository;
import com.dataart.cassandra.cassandracourse.repository.RoomsRepository;
import com.dataart.cassandra.cassandracourse.web.request.FreeRoomRequest;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Collections;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
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
public class BookingService {

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
        Mono<Booking> result = hotelsRepository.findById(b.getHotelId()).flatMap(hotel -> {
            if (hotel == null) {
                throw new ApplicationLogicException(String.format("Hotel %s not exists.", b.getHotelId()));
            }
            Mono<Room> roomMono = roomsRepository.getRoom(b.getHotelId(), b.getRoom());
            Mono<Booking> result_ = roomMono.flatMap(room -> {
                if (room == null) {
                    throw new ApplicationLogicException("Can not find such room for provided hotel.");
                }
                Mono<Guest> guestMono = guestRepository.findById(b.getGuest());
                Mono<Booking> result__ = guestMono.flatMap(guestItem -> {
                    if (guestItem == null) {
                        throw new ApplicationLogicException("Can not find guest with provided login.");
                    }
                    Mono<Integer> bookings = bookingDetailsRepository.getCreatedBookingCount(b.getHotelId(), b.getStartDate(), b.getEndDate(), b.getRoom());
                    Mono<Booking> bookingsMono = bookings.flatMap(cnt -> {
                        if (cnt > 0) {
                            throw new ApplicationLogicException("Room is already booked for this period.");
                        }
                        Mono<Booking> newBookingMono = helper.insertIfNotExists(b);
                        newBookingMono.subscribe(booking -> {
                            saveDataToBookingDetailsTable(booking);
                        });
                        return newBookingMono;
                    });
                    return bookingsMono;
                });
                return result__;
            });
            return result_;
        });
        return result;
    }

    private void saveDataToBookingDetailsTable(Booking b) {
        bookingDetailsRepository.saveAll(createBookingDetailFromBooking(b));
    }

    private List<BookingDetail> createBookingDetailFromBooking(Booking b) {
        List<BookingDetail> resList = new LinkedList<>();
        LocalDateTime ldStart = LocalDateTime.ofInstant(b.getStartDate().toInstant(),
                ZoneId.systemDefault());
        LocalDateTime ldEnd = LocalDateTime.ofInstant(b.getStartDate().toInstant(),
                ZoneId.systemDefault());
        while (ldStart.compareTo(ldEnd) <= 0) {
            resList.add(new BookingDetail(b.getHotelId(), new Date(ldStart.toLocalDate().toEpochDay()), b.getRoom(), b.getStartDate(), b.getEndDate()));
            ldStart = ldStart.plusDays(1);
        }
        return resList;
    }

    public Flux<Room> getFreeRooms(FreeRoomRequest request) {
        List<BookingDetail> lst = bookingDetailsRepository
                .getCreatedBookingDetails(request.getHotel(), request.getStartDate(), request.getEndDate())                
                .switchIfEmpty(alternate),
                .collectList().block();
        Flux<Booking> createdBookings = null;        
        if (lst!= null && !lst.isEmpty()) {
            BookingDetail detail = lst.get(0);
            createdBookings = bookingRepository.getCreatedBooking(
                    detail.getHotelId(), detail.getStartDate(), detail.getEndDate(), detail.getRoom());
            Map<Integer, Booking> bookings = createdBookings.collectMap(Booking::getRoom).block();
            return roomsRepository.getRoomsForHotel(request.getHotel()).filter(room -> {
                return bookings != null && !bookings.containsKey(room.getRoomNumber());
            });
        } else {
            return roomsRepository.getRoomsForHotel(request.getHotel());
        }                            
    }

    public Flux<Booking> getBookedRooms(FreeRoomRequest request) {
        return bookingDetailsRepository
                .getCreatedBookingDetails(request.getHotel(), request.getStartDate(), request.getEndDate())
                .elementAt(0).flatMapMany(detail -> {
            return detail != null ? bookingRepository.getCreatedBooking(
                    detail.getHotelId(), detail.getStartDate(), detail.getEndDate(), detail.getRoom()) : Flux.<Booking>empty();
        });
    }
}
