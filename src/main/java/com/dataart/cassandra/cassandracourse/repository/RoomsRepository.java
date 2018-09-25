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
public interface RoomsRepository extends ReactiveCassandraRepository<Room, String> {

    @Query("select * from demo.rooms where hotel_id = :hotel and room_number = :room")
    Mono<Room> getRoom(@Param("hotel") String hotel,
            @Param("room") int room);

    @Query("select * from demo.rooms where hotel_id = :hotel")
    Flux<Room> getRoomsForHotel(@Param("hotel") String hotel);
}
