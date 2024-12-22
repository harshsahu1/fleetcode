package com.fleetcode.question_service.model;

import lombok.Getter;

import java.util.List;

@Getter
public class QuestionDTO {
    private String question;
    private String difficulty;
    private List<String> topics;
}
