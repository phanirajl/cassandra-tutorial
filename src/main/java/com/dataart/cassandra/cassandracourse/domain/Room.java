package com.dataart.cassandra.cassandracourse.domain;

import java.math.BigDecimal;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import lombok.Data;
import static org.springframework.data.cassandra.core.cql.PrimaryKeyType.PARTITIONED;
import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn;
import org.springframework.data.cassandra.core.mapping.Table;

/**
 * Room in hotel.
 *
 * @author alitvinov
 */
@Data
@Table(value = "rooms")
public class Room {

    @PrimaryKeyColumn(name = "hotel_id", type = PARTITIONED)
    @NotNull
    private String hotel;

    @PrimaryKeyColumn(name = "room_number", type = PARTITIONED)
    @NotNull
    @Positive
    @Min(1L)
    private int roomNumber;

    @Column(value = "floor")
    @Positive
    @Min(1L)
    @NotNull
    private int floor;

    @NotNull
    @Column(value = "square")
    @Positive
    private BigDecimal square;

    @NotNull
    @Column(value = "price")
    @Positive
    private BigDecimal price;
}
