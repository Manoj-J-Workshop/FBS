package com.fbs.central_api.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class AirlineRejectDto {
    String airlineAdminName;
    String getAirlineAdminEmail;
    String rejectReason;
}
