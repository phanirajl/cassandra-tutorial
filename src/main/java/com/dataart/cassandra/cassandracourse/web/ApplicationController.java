package com.dataart.cassandra.cassandracourse.web;

import com.dataart.cassandra.cassandracourse.domain.Booking;
import com.dataart.cassandra.cassandracourse.domain.Guest;
import com.dataart.cassandra.cassandracourse.domain.Hotel;
import com.dataart.cassandra.cassandracourse.domain.Room;
import com.dataart.cassandra.cassandracourse.service.BookingService;
import com.dataart.cassandra.cassandracourse.service.HotelsService;
import com.dataart.cassandra.cassandracourse.service.CassandraOperationHelper;
import com.dataart.cassandra.cassandracourse.web.request.BookingRequest;
import com.dataart.cassandra.cassandracourse.web.request.FreeRoomRequest;
import com.dataart.cassandra.cassandracourse.web.request.GetHotelByCityRequest;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Application controller.
 *
 * @author alitvinov
 */
@RestController
public class ApplicationController {

    @Autowired
    private HotelsService hotelService;

    @Autowired
    private CassandraOperationHelper cassandraHelper;

    @Autowired
    private BookingService bookingService;

    @PostMapping("api/get/city")
    Flux<Hotel> citylist(@Valid @RequestBody GetHotelByCityRequest cityRequest) {
        return hotelService.getHotelsByCity(cityRequest.getCity());
    }

    @PostMapping("api/add/hotel")
    Mono<Hotel> add(@Valid @RequestBody Hotel hotel) {
        return cassandraHelper.insertIfNotExists(hotel);
    }

    @PostMapping("api/add/guest")
    Mono<Guest> addGuest(@Valid @RequestBody Guest guest) {
        return cassandraHelper.insertIfNotExists(guest);
    }

    @PostMapping("api/add/room")
    Mono<Room> addRoom(@Valid @RequestBody Room room) {
        return hotelService.addRoom(room);
    }

    @PostMapping("api/add/booking")
    Mono<Booking> addNewBooking(@Valid @RequestBody BookingRequest request) {
        return bookingService.addBooking(request.toBookingDomain());
    }

    @PostMapping("api/get/freerooms")
    Flux<Room> getFreeRooms(@Valid @RequestBody FreeRoomRequest request) {
        return bookingService.getFreeRooms(request);
    }

    @PostMapping("api/get/roombyguest")
    Flux<Booking> getRoomByGuest(@Valid @RequestBody FreeRoomRequest request) {
        return bookingService.getBookedRooms(request);
    }
}
