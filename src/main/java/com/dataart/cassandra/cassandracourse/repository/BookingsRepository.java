package com.dataart.cassandra.cassandracourse.repository;

import com.dataart.cassandra.cassandracourse.domain.Booking;
import com.dataart.cassandra.cassandracourse.domain.BookingPrimaryKey;
import org.springframework.data.cassandra.repository.ReactiveCassandraRepository;

/**
 *
 * @author alitvinov
 */
public interface BookingsRepository extends ReactiveCassandraRepository<Booking, BookingPrimaryKey> {

}
