/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dataart.cassandra.cassandracourse.web.request;

import com.dataart.cassandra.cassandracourse.domain.Booking;
import com.dataart.cassandra.cassandracourse.domain.BookingPrimaryKey;
import com.fasterxml.jackson.annotation.JsonFormat;
import java.math.BigDecimal;
import java.util.Date;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import lombok.Data;
import org.springframework.data.cassandra.core.cql.PrimaryKeyType;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn;

/**
 *
 * @author alitvinov
 */
@Data
public class BookingRequest {

    @PrimaryKeyColumn(name = "hotel_id", type = PrimaryKeyType.PARTITIONED)
    @NotNull(message = "Please, provide id of hotel (hotelId).")
    private String hotelId;

    @NotNull(message = "Please, provide booking start date (startDate).")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date startDate;

    @NotNull(message = "Please, provide booking end date (endDate).")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date endDate;

    @NotNull(message = "Please, provide booking room number (room).")
    @Positive
    private int room;

    @NotNull(message = "Please, provide booking price (price).")
    @Positive
    private BigDecimal price;

    @NotNull(message = "Please, provide number of guests (guestNumber).")
    @Min(value = 1L)
    @Positive
    private int guestNumber;

    @NotNull(message = "Please, provide login of user, which book room (login).")
    private String guest;

    public Booking toBookingDomain() {
        BookingPrimaryKey pk = new BookingPrimaryKey(hotelId, startDate, endDate, room);
        return new Booking(pk, price, guestNumber, guest);
    }
}
