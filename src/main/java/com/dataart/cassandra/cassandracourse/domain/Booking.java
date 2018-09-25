package com.dataart.cassandra.cassandracourse.domain;

import java.math.BigDecimal;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

/**
 * Booking record.
 *
 * @author alitvinov
 */
@Data
@Table(value = "bookings")
@AllArgsConstructor
@NoArgsConstructor
public class Booking {

    @PrimaryKey
    BookingPrimaryKey key;

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
