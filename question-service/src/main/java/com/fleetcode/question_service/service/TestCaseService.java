package com.fleetcode.question_service.service;

import com.fleetcode.question_service.model.Question;
import com.fleetcode.question_service.model.TestCaseDTO;
import com.fleetcode.question_service.model.TestCases;
import com.fleetcode.question_service.repository.TestCaseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TestCaseService {

    private TestCaseRepository testCaseRepository;
    private QuestionService questionService;

    @Autowired
    public TestCaseService(TestCaseRepository testCaseRepository, QuestionService questionService) {
        this.testCaseRepository = testCaseRepository;
        this.questionService = questionService;
    }
    public List<TestCaseDTO> getAllTestCases(Long questionId) {

        return testCaseRepository.findAllByQuestionId(questionId)
                .stream()
                .map(testCase -> {
                    TestCaseDTO dto = new TestCaseDTO();
                    dto.setTestCase(testCase.getTestCase());
                    dto.setExpectedResult(testCase.getExpectedResult());
                    return dto;
                })
                .collect(Collectors.toList());
    }


    public TestCases getTestCase(Long testCaseId) {
        return testCaseRepository.findById(testCaseId).get();
    }

    public TestCases saveTestCase(Long questionId, TestCaseDTO testCase) {
        Question question = questionService.findById(questionId)
                .orElseThrow(() -> new IllegalArgumentException("Question with ID " + questionId + " not found"));

        TestCases testCases = new TestCases();
        testCases.setTestCase(testCase.getTestCase());
        testCases.setExpectedResult(testCase.getExpectedResult());
        testCases.setQuestion(question);

        return testCaseRepository.save(testCases);
    }

}
