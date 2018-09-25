package com.dataart.cassandra.cassandracourse.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.util.Date;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.cassandra.core.cql.PrimaryKeyType;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyClass;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn;

/**
 * Primary key for Booking table.
 *
 * @author alitvinov
 */
@Data
@PrimaryKeyClass
@AllArgsConstructor
@NoArgsConstructor
public class BookingPrimaryKey {

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

}
