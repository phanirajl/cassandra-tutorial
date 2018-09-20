package com.dataart.cassandra.cassandracourse.web.request;

import javax.validation.constraints.NotNull;
import lombok.Data;

/**
 *
 * @author alitvinov
 */
@Data
public class GetHotelByCityRequest {
    
    @NotNull(message = "Please, specify city for search.")
    private String city;
    
}
