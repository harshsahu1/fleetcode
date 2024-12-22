package com.fleetcode.question_service.controller;

import com.fleetcode.question_service.model.TestCaseDTO;
import com.fleetcode.question_service.model.TestCases;
import com.fleetcode.question_service.service.TestCaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/question/{question_id}/testcases")
public class TestCaseController {

    private final TestCaseService testCaseService;

    @Autowired
    public TestCaseController(TestCaseService testCaseService) {
        this.testCaseService = testCaseService;
    }

    @GetMapping("/all")
    @Transactional(readOnly = true)
    public List<TestCaseDTO> getAllTestCases(@PathVariable Long question_id) {
        return testCaseService.getAllTestCases(question_id);
    }

    @PostMapping
    public TestCases createTestCase(@PathVariable Long question_id, @RequestBody TestCaseDTO testCase) {
        return testCaseService.saveTestCase(question_id, testCase);
    }
}
