package com.fleetcode.submission_service.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SubmissionEvent implements Serializable {

    private Long submissionId;
    private Long userId;
    private Long questionId;
    private String language;
    private String code;
    private String timestamp;
}

