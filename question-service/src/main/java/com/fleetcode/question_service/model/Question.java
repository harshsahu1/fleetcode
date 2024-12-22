package com.fleetcode.question_service.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "questions")
@RequiredArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "question", nullable = false)
    private String question;  // Stores the JSON structure for the question

    @Column(name = "difficulty", nullable = false)
    private String difficulty;  // Difficulty like "easy", "medium", "hard"

    @Column(name = "total_attempts", nullable = false, columnDefinition = "int default 0")
    private int totalAttempts;

    @Column(name = "total_accepted", nullable = false, columnDefinition = "int default 0")
    private int totalAccepted;

    @Column(name = "total_submissions", nullable = false, columnDefinition = "int default 0")
    private int totalSubmissions;

    @Column(name = "total_submissions_accepted", nullable = false, columnDefinition = "int default 0")
    private int totalSubmissionsAccepted;

    // Many-to-many relationship with Topic
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "question_topic",
            joinColumns = @JoinColumn(name = "question_id"),
            inverseJoinColumns = @JoinColumn(name = "topic_id")
    )
    private List<Topic> topics;
}
