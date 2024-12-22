package com.fleetcode.user_service.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateUserPerformanceDTO {
    private Long userId;
    private Boolean wasAccepted;
    private Boolean wasNewQuestion;
}
