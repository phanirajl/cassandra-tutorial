package com.dataart.cassandra.cassandracourse.domain;

import javax.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

/**
 * Guest record.
 *
 * @author alitvinov
 */
@Data
@Table(value = "guests")
public class Guest {

    @PrimaryKey(value = "login")
    @NotNull(message = "Provide login of guest.")
    private String login;

    @Column(value = "first_name")
    @NotNull(message = "Provide first name of guest.")
    private String firstName;

    @Column(value = "last_name")
    @NotNull(message = "Provide last of guest.")
    private String lastName;
}
