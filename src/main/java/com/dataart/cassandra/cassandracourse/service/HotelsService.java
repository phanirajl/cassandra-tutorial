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
public class HotelsService{

    @Autowired
    private HotelsRepository hotelRepository;
    
    @Autowired
    private CassandraOperationHelper cassandraHelper;
    
    /**
     * Get all hotels by city.
     *
     * @param city name of city
     * @return all hotels for this city
     *
     * @see According to task description, should be async, it is implemented
     * using Flux.
     */
    public Flux<Hotel> getHotelsByCity(String city) {                        
        return hotelRepository.findByCity(city);
    }
    
    /**
     * Adding of room to hotel.
     * 
     * @param room room
     * @return Room instance
     */
    public Mono<Room> addRoom(Room room) {                
        Boolean hotelExists = hotelRepository.existsById(room.getHotel()).block();
        if (!hotelExists) {
            throw new ApplicationLogicException(String.format("Hotel with id %s note exists", room.getHotel()));
        }
        return cassandraHelper.insertIfNotExists(room);
    }
    
}
