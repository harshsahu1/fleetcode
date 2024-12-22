package com.fleetcode.question_service.repository;

import com.fleetcode.question_service.model.TestCases;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TestCaseRepository extends JpaRepository<TestCases, Long> {
    @Query("SELECT tc FROM TestCases tc WHERE tc.question.id = :questionId")
    List<TestCases> findAllByQuestionId(@Param("questionId") Long questionId);
}
