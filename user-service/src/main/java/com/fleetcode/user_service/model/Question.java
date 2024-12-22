package com.fleetcode.user_service.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "questions")
@RequiredArgsConstructor
@Getter
@Setter
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Lob  // This will store the JSON data in the database
    @Column(name = "question", columnDefinition = "json")
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
    @ManyToMany
    @JoinTable(
            name = "question_topic",
            joinColumns = @JoinColumn(name = "question_id"),
            inverseJoinColumns = @JoinColumn(name = "topic_id")
    )
    private List<Topic> topics;
}
