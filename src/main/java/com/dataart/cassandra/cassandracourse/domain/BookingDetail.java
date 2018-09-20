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

    @PrimaryKeyColumn(name = "booking_date", type = PrimaryKeyType.PARTITIONED)        
    private Date bookingDate;

    @PrimaryKeyColumn(name = "room", type = PrimaryKeyType.PARTITIONED)    
    private int room;    

    @Column(value = "start_date")    
    private Date startDate;

    @Column(value = "end_date")    
    private Date endDate;  
}
