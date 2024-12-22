package com.fleetcode.question_service.repository;

import com.fleetcode.question_service.model.Question;
import com.fleetcode.question_service.model.Topic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuestionRepository extends JpaRepository<Question, Long> {

    @Query("SELECT q FROM Question q WHERE q.difficulty = :difficulty")
    List<Question> findByDifficulty(@Param("difficulty") String difficulty);

    @Query("SELECT q FROM Question q JOIN q.topics t WHERE t.title IN :titles")
    List<Question> findByTopicsIn(@Param("titles") List<String> titles);
}

