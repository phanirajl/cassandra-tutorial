package com.dataart.cassandra.cassandracourse.repository;

import com.dataart.cassandra.cassandracourse.domain.Guest;
import org.springframework.data.cassandra.repository.ReactiveCassandraRepository;

/**
 *
 * @author alitvinov
 */
public interface GuestRepository  extends ReactiveCassandraRepository<Guest, String>{
    
}
