package com.fleetcode.submission_service.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SubmissionDTO {
    private Long userId;
    private Long questionId;
    private String language;
    private String code;
}

