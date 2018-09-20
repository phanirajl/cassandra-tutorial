package com.dataart.cassandra.cassandracourse.service;

import com.datastax.driver.core.Cluster;
import javax.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.cognitor.cassandra.migration.Database;
import org.cognitor.cassandra.migration.MigrationRepository;
import org.cognitor.cassandra.migration.MigrationTask;
import org.cognitor.cassandra.migration.keyspace.Keyspace;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 *
 * @author alitvinov
 */
@Component(value = "dataInitializer")
@Slf4j
public class CassandraDataInitializer {

    @Value("${cassandra.create-schema:true}")
    private boolean createSchema;

    @Autowired
    private Cluster cluster;

    public final static String KEYSPACE_NAME = "demo";
    
    @PostConstruct
    private void afterPropertiesSet() {
        if (createSchema) {
            log.info("Try to create schema.");
            Database database = new Database(cluster, new Keyspace(KEYSPACE_NAME));
            MigrationTask migration = new MigrationTask(database, new MigrationRepository());
            migration.migrate();
            log.info("Creation of schema finished succesfully.");
        }
    }
}
