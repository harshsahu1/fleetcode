package com.fleetcode.user_service.service;

import com.fleetcode.user_service.model.User;
import com.fleetcode.user_service.model.UserPerformance;
import com.fleetcode.user_service.repository.UserPerformanceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class UserPerformanceService {

    private final UserPerformanceRepository userPerformanceRepository;

    @Autowired
    private UserPerformanceService(UserPerformanceRepository userPerformanceRepository) {
        this.userPerformanceRepository = userPerformanceRepository;
    }

    public void initUserPerformance(User user) {
        UserPerformance userPerformance = UserPerformance.builder()
                .user(user)
                .rating(1000)
                .lastActiveAt(LocalDateTime.now())
                .highestStreak(1)
                .build();
        userPerformanceRepository.save(userPerformance);
    }

    public Optional<UserPerformance> getUserPerformance(Long userId) {
        return userPerformanceRepository.findByUserId(userId);
    }

    public Optional<UserPerformance> updateUserPerformance(Long userId, Boolean wasAccepted, Boolean wasNewQuestion) {
        Optional<UserPerformance> userPerformanceOptional = userPerformanceRepository.findByUserId(userId);
        if (userPerformanceOptional.isPresent()) {
            UserPerformance userPerformance = userPerformanceOptional.get();

            int prevQuestionSolvedCount = userPerformance.getQuestionsSolvedCount();
            int prevQuestionsAttemptedCount = userPerformance.getQuestionsAttemptedCount();
            int prevSubmissionCount = userPerformance.getSubmissionsCount();
            int prevRating = userPerformance.getRating();
            int prevHighestStreak = userPerformance.getHighestStreak();
            LocalDateTime prevLastActiveAt = userPerformance.getLastActiveAt();

            if (wasAccepted) {
                if (wasNewQuestion) {
                    userPerformance.setQuestionsAttemptedCount(prevQuestionsAttemptedCount + 1);
                    userPerformance.setQuestionsSolvedCount(prevQuestionSolvedCount + 1);
                }
                userPerformance.setRating(prevRating + 10);
            } else {
                if (wasNewQuestion) {
                    userPerformance.setQuestionsAttemptedCount(prevQuestionsAttemptedCount + 1);
                }
                userPerformance.setRating(prevRating - 10);
            }
            if (prevLastActiveAt != null && prevLastActiveAt.toLocalDate().equals(LocalDate.now().minusDays(1))) {
                userPerformance.setHighestStreak(prevHighestStreak + 1);
            }
            else if (prevLastActiveAt != null && prevLastActiveAt.toLocalDate().equals(LocalDate.now().minusDays(0))) {
                userPerformance.setHighestStreak(prevHighestStreak);
            }else {
                userPerformance.setHighestStreak(1);
            }
            userPerformance.setLastActiveAt(LocalDateTime.now());
            userPerformance.setSubmissionsCount(prevSubmissionCount + 1);
            userPerformanceRepository.save(userPerformance);

            return Optional.of(userPerformance);
        }
        return Optional.empty();
    }
}
