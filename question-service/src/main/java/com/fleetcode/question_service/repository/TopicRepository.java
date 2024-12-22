package com.fleetcode.question_service.repository;

import com.fleetcode.question_service.model.Topic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TopicRepository extends JpaRepository<Topic, Integer> {
    List<Topic> findByTitleIn(List<String> titles);
}
