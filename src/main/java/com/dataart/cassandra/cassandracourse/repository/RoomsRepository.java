package com.dataart.cassandra.cassandracourse.repository;

import com.dataart.cassandra.cassandracourse.domain.Room;
import org.springframework.data.cassandra.repository.Query;
import org.springframework.data.cassandra.repository.ReactiveCassandraRepository;
import org.springframework.data.repository.query.Param;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 *
 * @author alitvinov
 */
public interface RoomsRepository extends ReactiveCassandraRepository<Room, String>{
    
    @Query("select r from Room r where hotel = :hotel and room = :room")
    Mono<Room> getRoom(@Param("hotel") String hotel, 
            @Param("room") int room);
    
    @Query("select r from Room r where hotel = :hotel")
    Flux<Room> getRoomsForHotel(@Param("hotel") String hotel);
}
