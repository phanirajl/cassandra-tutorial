package com.dataart.cassandra.cassandracourse.web.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.util.Date;
import javax.validation.constraints.NotNull;
import lombok.Data;

/**
 *
 * @author alitvinov
 */
@Data
public class FreeRoomRequest {

    @NotNull
    @JsonFormat(pattern = "yyyy-MM-dd HH")
    private String hotel;
    
    @NotNull
    @JsonFormat(pattern = "yyyy-MM-dd HH")
    private Date startDate;
    
    @NotNull
    @JsonFormat(pattern = "yyyy-MM-dd HH")
    private Date endDate;
}
