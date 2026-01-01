package com.fleetcode.submission_service.services;

import com.fleetcode.submission_service.dto.SubmissionDTO;
import com.fleetcode.submission_service.dto.SubmissionEvent;
import com.fleetcode.submission_service.dto.SubmissionResponse;
import com.fleetcode.submission_service.models.Submission;
import com.fleetcode.submission_service.models.SubmissionStatus;
import com.fleetcode.submission_service.repository.SubmissionRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

@Service
@Slf4j
public class SubmissionService {

    private final SubmissionRepository submissionRepository;
    private final KafkaTemplate<String, SubmissionEvent> kafkaTemplate;

    @Value("${kafka.topic.submission}")
    private String submissionTopic;

    @Autowired
    public SubmissionService(SubmissionRepository submissionRepository,
                             KafkaTemplate<String, SubmissionEvent> kafkaTemplate) {
        this.submissionRepository = submissionRepository;
        this.kafkaTemplate = kafkaTemplate;
    }

    public SubmissionResponse createSubmission(SubmissionDTO submissionDTO) {
        Submission submission = Submission.builder()
                .userId(submissionDTO.getUserId())
                .questionId(submissionDTO.getQuestionId())
                .language(submissionDTO.getLanguage())
                .code(submissionDTO.getCode())
                .status(SubmissionStatus.PENDING)
                .build();

        Submission savedSubmission = submissionRepository.save(submission);
        log.info("Submission saved with ID: {}", savedSubmission.getId());

        SubmissionEvent event = SubmissionEvent.builder()
                .submissionId(savedSubmission.getId())
                .userId(savedSubmission.getUserId())
                .questionId(savedSubmission.getQuestionId())
                .language(savedSubmission.getLanguage())
                .code(savedSubmission.getCode())
                .timestamp(LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME))
                .build();

        sendToKafkaAsync(event);

        return SubmissionResponse.builder()
                .id(savedSubmission.getId())
                .userId(savedSubmission.getUserId())
                .questionId(savedSubmission.getQuestionId())
                .language(savedSubmission.getLanguage())
                .status(savedSubmission.getStatus())
                .message("Submission received and queued for processing")
                .createdAt(savedSubmission.getCreatedAt())
                .build();
    }

    private void sendToKafkaAsync(SubmissionEvent event) {
        String key = String.valueOf(event.getSubmissionId());

        CompletableFuture<SendResult<String, SubmissionEvent>> future =
                kafkaTemplate.send(submissionTopic, key, event);

        future.whenComplete((result, ex) -> {
            if (ex == null) {
                log.info("Submission event sent successfully to Kafka. Topic: {}, Partition: {}, Offset: {}",
                        result.getRecordMetadata().topic(),
                        result.getRecordMetadata().partition(),
                        result.getRecordMetadata().offset());
            } else {
                log.error("Failed to send submission event to Kafka for submission ID: {}. Error: {}",
                        event.getSubmissionId(), ex.getMessage());
                updateSubmissionStatus(event.getSubmissionId(), SubmissionStatus.PENDING,
                        "Failed to queue for processing: " + ex.getMessage());
            }
        });
    }

    public void updateSubmissionStatus(Long submissionId, SubmissionStatus status, String result) {
        Optional<Submission> submissionOpt = submissionRepository.findById(submissionId);
        if (submissionOpt.isPresent()) {
            Submission submission = submissionOpt.get();
            submission.setStatus(status);
            submission.setResult(result);
            submissionRepository.save(submission);
            log.info("Updated submission {} status to {}", submissionId, status);
        }
    }

    public Optional<Submission> getSubmissionById(Long id) {
        return submissionRepository.findById(id);
    }

    public List<Submission> getSubmissionsByUserId(Long userId) {
        return submissionRepository.findByUserIdOrderByCreatedAtDesc(userId);
    }

    public List<Submission> getSubmissionsByQuestionId(Long questionId) {
        return submissionRepository.findByQuestionIdOrderByCreatedAtDesc(questionId);
    }

    public List<Submission> getSubmissionsByUserAndQuestion(Long userId, Long questionId) {
        return submissionRepository.findByUserIdAndQuestionId(userId, questionId);
    }

    public List<Submission> getSubmissionsByStatus(SubmissionStatus status) {
        return submissionRepository.findByStatus(status);
    }
}

