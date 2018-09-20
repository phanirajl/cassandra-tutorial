package com.dataart.cassandra.cassandracourse.repository;

import com.dataart.cassandra.cassandracourse.domain.Hotel;
import org.springframework.data.cassandra.repository.ReactiveCassandraRepository;
import reactor.core.publisher.Flux;

/**
 *
 * @author alitvinov
 */
public interface HotelsRepository extends ReactiveCassandraRepository<Hotel, String>{
    
    Flux<Hotel> findByCity(String city);    
    
}
