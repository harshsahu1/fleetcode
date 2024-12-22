package com.fleetcode.question_service.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "testcases")
@RequiredArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class TestCases {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name = "question_id", nullable = false)
    private Question question;

    @Column(name = "test_case", nullable = false)
    private String testCase;

    @Column(name = "expected_output", nullable = false)
    private String expectedResult;
}
