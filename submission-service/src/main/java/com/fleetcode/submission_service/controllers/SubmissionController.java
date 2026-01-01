package com.fleetcode.submission_service.controllers;

import com.fleetcode.submission_service.dto.SubmissionDTO;
import com.fleetcode.submission_service.dto.SubmissionResponse;
import com.fleetcode.submission_service.models.Submission;
import com.fleetcode.submission_service.models.SubmissionStatus;
import com.fleetcode.submission_service.services.SubmissionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/submissions")
@Tag(name = "Submission", description = "Code Submission APIs")
public class SubmissionController {

    private final SubmissionService submissionService;

    @Autowired
    public SubmissionController(SubmissionService submissionService) {
        this.submissionService = submissionService;
    }

    @PostMapping
    @Operation(summary = "Submit code for evaluation",
               description = "Accepts a code submission and queues it for async processing via Kafka")
    public ResponseEntity<SubmissionResponse> submitCode(@RequestBody SubmissionDTO submissionDTO) {
        SubmissionResponse response = submissionService.createSubmission(submissionDTO);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(response);
    }

    @GetMapping("/{submission_id}")
    @Operation(summary = "Get submission by ID", description = "Retrieves a submission and its current status")
    public ResponseEntity<Submission> getSubmission(@PathVariable("submission_id") Long submissionId) {
        return submissionService.getSubmissionById(submissionId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/user/{user_id}")
    @Operation(summary = "Get submissions by user", description = "Retrieves all submissions for a specific user")
    public ResponseEntity<List<Submission>> getSubmissionsByUser(@PathVariable("user_id") Long userId) {
        List<Submission> submissions = submissionService.getSubmissionsByUserId(userId);
        if (submissions.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(submissions);
    }

    @GetMapping("/question/{question_id}")
    @Operation(summary = "Get submissions by question", description = "Retrieves all submissions for a specific question")
    public ResponseEntity<List<Submission>> getSubmissionsByQuestion(@PathVariable("question_id") Long questionId) {
        List<Submission> submissions = submissionService.getSubmissionsByQuestionId(questionId);
        if (submissions.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(submissions);
    }

    @GetMapping("/user/{user_id}/question/{question_id}")
    @Operation(summary = "Get submissions by user and question",
               description = "Retrieves all submissions for a specific user on a specific question")
    public ResponseEntity<List<Submission>> getSubmissionsByUserAndQuestion(
            @PathVariable("user_id") Long userId,
            @PathVariable("question_id") Long questionId) {
        List<Submission> submissions = submissionService.getSubmissionsByUserAndQuestion(userId, questionId);
        if (submissions.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(submissions);
    }

    @GetMapping("/status/{status}")
    @Operation(summary = "Get submissions by status", description = "Retrieves all submissions with a specific status")
    public ResponseEntity<List<Submission>> getSubmissionsByStatus(@PathVariable("status") SubmissionStatus status) {
        List<Submission> submissions = submissionService.getSubmissionsByStatus(status);
        if (submissions.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(submissions);
    }
}

