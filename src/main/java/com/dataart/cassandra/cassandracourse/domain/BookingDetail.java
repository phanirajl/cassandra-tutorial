package com.dataart.cassandra.cassandracourse.domain;

import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.cassandra.core.cql.PrimaryKeyType;
import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn;
import org.springframework.data.cassandra.core.mapping.Table;

/**
 * Booking detail record.
 *
 * is used as helper table for Booking, that gives an opportunity to search with
 * date range.
 *
 * @author alitvinov
 */
@Data
@Table(value = "bookings_period_detail")
@AllArgsConstructor
@NoArgsConstructor
public class BookingDetail {

    @PrimaryKeyColumn(name = "hotel_id", type = PrimaryKeyType.PARTITIONED)
    private String hotelId;

    @PrimaryKeyColumn(name = "booking_date", type = PrimaryKeyType.CLUSTERED)
    private Date bookingDate;

    @PrimaryKeyColumn(name = "room", type = PrimaryKeyType.CLUSTERED)
    private int room;

    @Column(value = "start_date")
    private Date startDate;

    @Column(value = "end_date")
    private Date endDate;
}
