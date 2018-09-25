package com.dataart.cassandra.cassandracourse.service;

import com.dataart.cassandra.cassandracourse.domain.Hotel;
import com.dataart.cassandra.cassandracourse.domain.Room;
import com.dataart.cassandra.cassandracourse.exception.ApplicationLogicException;
import com.dataart.cassandra.cassandracourse.repository.HotelsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 *
 * @author alitvinov
 */
@Component
public class HotelsService {

    @Autowired
    private HotelsRepository hotelRepository;

    @Autowired
    private CassandraOperationHelper cassandraHelper;

    public Flux<Hotel> getHotelsByCity(String city) {
        return hotelRepository.findByCity(city);

    }

    public Mono<Room> addRoom(Room room) {
        Mono<Boolean> hotelExists
                = hotelRepository.existsById(room.getHotel());

        Mono<Room> addRoomResult = hotelExists.flatMap(b -> {
            if (!b) {
                throw new ApplicationLogicException(String.format("Hotel %s not exists.", room.getHotel()));
            }
            return cassandraHelper.insertIfNotExists(room);
        });
        return addRoomResult;
    }

}
