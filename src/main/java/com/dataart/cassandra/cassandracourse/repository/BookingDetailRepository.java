/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dataart.cassandra.cassandracourse.repository;

import com.dataart.cassandra.cassandracourse.domain.BookingDetail;
import java.util.Date;
import org.springframework.data.cassandra.repository.Query;
import org.springframework.data.cassandra.repository.ReactiveCassandraRepository;
import org.springframework.data.repository.query.Param;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 *
 * @author alitvinov
 */
public interface BookingDetailRepository extends ReactiveCassandraRepository<BookingDetail, String>{
    
    @Query(value = "select count(*) from demo.bookings_period_detail where hotel_id = :hotel and booking_date>=:start and booking_date<=:end room = :room")
    Mono<Integer> getCreatedBookingCount(
            @Param("hotel") String hotel,
            @Param("start") Date start, 
            @Param("end") Date end, 
            @Param("room") int room);   
    
    @Query(value = "select * from demo.bookings_period_detail where hotel_id = :hotel and booking_date>=:start and booking_date<=:end ALLOW FILTERING")
    Flux<BookingDetail> getCreatedBookingDetails(
            @Param("hotel") String hotel,
            @Param("start") Date start, 
            @Param("end") Date end);   
}
