package com.dataart.cassandra.cassandracourse.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.math.BigDecimal;
import java.util.Date;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import lombok.Data;
import org.springframework.data.cassandra.core.cql.PrimaryKeyType;
import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn;
import org.springframework.data.cassandra.core.mapping.Table;

/**
 *
 * @author alitvinov
 */
@Data
@Table(value = "bookings")
public class Booking {

    @PrimaryKeyColumn(name = "hotel_id", type = PrimaryKeyType.PARTITIONED)
    @NotNull(message = "Please, provide id of hotel (hotelId).")
    private String hotelId;

    @PrimaryKeyColumn(name = "start_date", type = PrimaryKeyType.CLUSTERED)
    @NotNull(message = "Please, provide booking start date (startDate).")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date startDate;

    @PrimaryKeyColumn(name = "end_date", type = PrimaryKeyType.CLUSTERED)
    @NotNull(message = "Please, provide booking end date (endDate).")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date endDate;

    @PrimaryKeyColumn(name = "room", type = PrimaryKeyType.CLUSTERED)
    @NotNull(message = "Please, provide booking room number (room).")
    @Positive
    private int room;    

    @Column(value = "price")
    @NotNull(message = "Please, provide booking price (price).")
    @Positive
    private BigDecimal price;

    @Column(value = "guest_number")
    @NotNull(message = "Please, provide number of guests (guestNumber).")
    @Min(value = 1L)
    @Positive
    private int guestNumber;

    @Column(value = "login")
    @NotNull(message = "Please, provide login of user, which book room (login).")
    private String guest;
}
