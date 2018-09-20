package com.dataart.cassandra.cassandracourse.domain;

import java.io.Serializable;
import javax.validation.constraints.NotNull;

import lombok.Data;
import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.Indexed;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

/**
 *
 * @author alitvinov
 */
@Table(value = "hotels")
@Data
public class Hotel implements Serializable {

    private static final long serialVersionUID = 1L;

    @PrimaryKey
    @NotNull(message = "Please, specify id of hotel.")
    private String id;

    @Column
    @NotNull(message = "Please, specify hotel name.")
    private String name;

    @Column
    @NotNull(message = "Please, specify hotel country.")
    private String country;

    @Column
    @NotNull(message = "Please, specify hotel city.")
    @Indexed
    private String city;

    @Column
    @NotNull(message = "Please, specify hotel owner.")
    private String owner;
}
