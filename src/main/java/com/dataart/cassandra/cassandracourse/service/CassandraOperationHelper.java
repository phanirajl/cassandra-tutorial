package com.dataart.cassandra.cassandracourse.service;

import com.dataart.cassandra.cassandracourse.exception.PrimaryKeyDuplicateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.cassandra.core.InsertOptions;
import org.springframework.data.cassandra.core.ReactiveCassandraOperations;
import org.springframework.data.cassandra.core.WriteResult;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

/**
 * Helper class for performing operation with Cassandra.
 *
 * @author alitvinov
 */
@Component
public class CassandraOperationHelper {

    @Autowired
    private ReactiveCassandraOperations reactiveCassandraTemplate;

    /**
     * Adding of new item to Cassandra.
     *
     * @param item item to add.
     * @return added item.
     *
     * @throws PrimaryKeyDuplicateException if item with such id exists.
     * @see According to task description, should be async, it is implemented
     * using Spring Reactor.
     * @see
     * https://docs.spring.io/spring/docs/5.0.0.BUILD-SNAPSHOT/spring-framework-reference/html/web-reactive.html
     *
     */
    public <T> Mono<T> insertIfNotExists(final T item) {
        Mono<WriteResult> wr = reactiveCassandraTemplate.insert(item, InsertOptions.builder().withIfNotExists().build());
        return wr.map(res -> {
            if (!res.wasApplied()) {
                throw new PrimaryKeyDuplicateException(
                        String.format(
                                "Can not add item %s. Primary key duplication.",
                                item.toString()));
            }
            return item;
        });
    }

}
