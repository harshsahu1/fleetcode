package com.fleetcode.comment_service.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "comments")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;  // Unique identifier for each comment

    @JoinColumn(name = "user_id", nullable = false)
    private Long userId;  // Reference to the User entity

    @JoinColumn(name = "question_id", nullable = false)
    private Long question;  // Reference to the Question entity

    @Column(name = "comment_text", nullable = false)
    private String commentText;  // Content of the comment

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;  // Timestamp when the comment was made

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;  // Timestamp for last update
}
