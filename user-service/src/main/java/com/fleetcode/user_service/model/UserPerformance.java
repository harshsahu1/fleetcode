package com.fleetcode.user_service.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "user_performance")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class UserPerformance {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "questions_solved_count", nullable = false, columnDefinition = "int default 0")
    private int questionsSolvedCount;

    @Column(name = "submissions_count", nullable = false, columnDefinition = "int default 0")
    private int submissionsCount;

    @Column(name = "questions_attempted_count", nullable = false, columnDefinition = "int default 0")
    private int questionsAttemptedCount;

    @Column(name = "rating", nullable = false, columnDefinition = "int default 1000")
    private int rating;

    @Column(name = "highest_streak", nullable = false, columnDefinition = "int default 0")
    private int highestStreak;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    @Column(name = "last_active_at", nullable = false)
    private LocalDateTime lastActiveAt;

    @PrePersist
    public void prePersist() {
        this.lastActiveAt = LocalDateTime.now();
    }

    @PreUpdate
    public void preUpdate() {
        this.lastActiveAt = LocalDateTime.now();
    }
}
