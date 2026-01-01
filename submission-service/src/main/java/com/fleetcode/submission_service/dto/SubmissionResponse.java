package com.fleetcode.submission_service.dto;

import com.fleetcode.submission_service.models.SubmissionStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SubmissionResponse {
    private Long id;
    private Long userId;
    private Long questionId;
    private String language;
    private SubmissionStatus status;
    private String message;
    private LocalDateTime createdAt;
}

