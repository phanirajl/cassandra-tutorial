package com.dataart.cassandra.cassandracourse.repository;

import com.dataart.cassandra.cassandracourse.domain.Booking;
import java.util.Date;
import org.springframework.data.cassandra.repository.Query;
import org.springframework.data.cassandra.repository.ReactiveCassandraRepository;
import org.springframework.data.repository.query.Param;
import reactor.core.publisher.Flux;

/**
 *
 * @author alitvinov
 */
public interface BookingsRepository extends ReactiveCassandraRepository<Booking, String>{
        
    @Query(value = "select * from demo.bookings where hotel_id = :hotel and start_date = :start and end_date = :end and room = :room")
    Flux<Booking> getCreatedBooking(@Param("hotel") String hotel,
            @Param("start") Date start, 
            @Param("end") Date end,
            @Param("room") int room);
}
