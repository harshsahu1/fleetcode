package com.fleetcode.question_service.controller;

import com.fleetcode.question_service.model.*;
import com.fleetcode.question_service.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/question")
public class QuestionsController {

    private final QuestionService questionService;

    @Autowired
    public QuestionsController(QuestionService questionService) {
        this.questionService = questionService;
    }

    @GetMapping("/all")
    public List<QuestionResDTO> getAllQuestions() {
        return questionService.findAll()
                .stream()
                .map(QuestionResDTO::new)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<QuestionResDTO> getQuestionById(@PathVariable Long id) {
        return questionService.findById(id)
                .map(question -> QuestionResDTO.builder()
                        .id(question.getId())
                        .question(question.getQuestion())
                        .difficulty(question.getDifficulty())
                        .totalAttempts(question.getTotalAttempts())
                        .totalAccepted(question.getTotalAccepted())
                        .totalSubmissions(question.getTotalSubmissions())
                        .totalSubmissionsAccepted(question.getTotalSubmissionsAccepted())
                        .topic(question.getTopics().stream().map(Topic::getTitle).collect(Collectors.toList()))
                        .build())
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping()
    @Transactional
    public ResponseEntity<QuestionResDTO> addQuestion(@RequestBody QuestionDTO questionDTO) {
        return questionService.createQuestion(questionDTO)
                .map(question -> QuestionResDTO.builder()
                        .id(question.getId())
                        .question(question.getQuestion())
                        .difficulty(question.getDifficulty())
                        .totalAttempts(question.getTotalAttempts())
                        .totalAccepted(question.getTotalAccepted())
                        .totalSubmissions(question.getTotalSubmissions())
                        .totalSubmissionsAccepted(question.getTotalSubmissionsAccepted())
                        .topic(question.getTopics().stream().map(Topic::getTitle).collect(Collectors.toList()))
                        .build())
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/update/{id}")
    public ResponseEntity<QuestionResDTO> updateQuestion(@PathVariable Long id, @RequestBody QuestionDTO questionDTO) {
        return questionService.updateQuestion(id, questionDTO)
                .map(question -> QuestionResDTO.builder()
                        .id(question.getId())
                        .question(question.getQuestion())
                        .difficulty(question.getDifficulty())
                        .totalAttempts(question.getTotalAttempts())
                        .totalAccepted(question.getTotalAccepted())
                        .totalSubmissions(question.getTotalSubmissions())
                        .totalSubmissionsAccepted(question.getTotalSubmissionsAccepted())
                        .topic(question.getTopics().stream().map(Topic::getTitle).collect(Collectors.toList()))
                        .build())
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public Void deleteQuestion(@PathVariable Long id) {
        questionService.deleteQuestion(id);
        return null;
    }

    @GetMapping("/difficulty/{difficulty}")
    public List<QuestionResDTO> getQuestionsByDifficulty(@PathVariable String difficulty) {
        return questionService.findQuestionsByDifficulty(difficulty)
                .stream()
                .map(QuestionResDTO::new)
                .collect(Collectors.toList());
    }

    @PostMapping("/topics")
    public List<QuestionResDTO> getQuestionsByTopic(@RequestBody List<TopicDTO> topicDTOS) {
        return questionService.findQuestionsByTopics(topicDTOS)
                .stream()
                .map(QuestionResDTO::new)
                .collect(Collectors.toList());
    }
}
