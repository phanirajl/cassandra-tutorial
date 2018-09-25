package com.dataart.cassandra.cassandracourse.repository;

import com.dataart.cassandra.cassandracourse.domain.BookingDetail;
import java.util.Date;
import org.springframework.data.cassandra.repository.Query;
import org.springframework.data.cassandra.repository.ReactiveCassandraRepository;
import org.springframework.data.repository.query.Param;
import reactor.core.publisher.Flux;

/**
 *
 * @author alitvinov
 */
public interface BookingDetailRepository extends ReactiveCassandraRepository<BookingDetail, String> {

    @Query(value = "select * from demo.bookings_period_detail where hotel_id = :hotel and booking_date>=:start and booking_date<=:end")
    Flux<BookingDetail> getCreatedBookingDetails(
            @Param("hotel") String hotel,
            @Param("start") Date start,
            @Param("end") Date end);
}
