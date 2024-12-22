package com.fleetcode.question_service.model;

import lombok.*;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@RequiredArgsConstructor
@AllArgsConstructor
@Builder
public class QuestionResDTO {
    private Long id;
    private String question;
    private String difficulty;
    private int totalAttempts;
    private int totalAccepted;
    private int totalSubmissions;
    private int totalSubmissionsAccepted;
    private List<String> topic;

    public QuestionResDTO(Question question) {
        this.id = question.getId();
        this.question = question.getQuestion();
        this.difficulty = question.getDifficulty();
        this.totalAttempts = question.getTotalAttempts();
        this.totalAccepted = question.getTotalAccepted();
        this.totalSubmissions = question.getTotalSubmissions();
        this.totalSubmissionsAccepted = question.getTotalSubmissionsAccepted();
        this.topic = question.getTopics().stream().map(Topic::getTitle).collect(Collectors.toList());
    }
}
