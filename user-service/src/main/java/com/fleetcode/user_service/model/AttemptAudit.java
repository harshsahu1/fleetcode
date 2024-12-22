package com.fleetcode.user_service.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "attempt_audit")
@RequiredArgsConstructor
@Getter
@Setter
public class AttemptAudit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;  // Unique identifier for the attempt

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;  // Reference to the User entity

    @ManyToOne
    @JoinColumn(name = "question_id", nullable = false)
    private Question question;  // Reference to the Question entity

    @Column(name = "submission_token", nullable = false)
    private String submissionToken;

    @Column(name = "attempt_status", nullable = false)
    @Enumerated(EnumType.STRING)
    private AttemptStatus attemptStatus;  // State of the attempt: SUCCESS or FAILED

    @Column(name = "time_complexity", nullable = true)
    private String time;  // Time complexity 10 MSec

    @Column(name = "space_complexity", nullable = true)
    private String space;  // Space complexity 10 MB

    @Column(name = "note")
    private String note;  // Additional notes about the attempt

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;  // Timestamp when the attempt was made

}
