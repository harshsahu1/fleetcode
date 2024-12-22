package com.fleetcode.question_service.service;

import com.fleetcode.question_service.model.Topic;
import com.fleetcode.question_service.model.TopicDTO;
import com.fleetcode.question_service.repository.TopicRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TopicService {

    private final TopicRepository topicRepository;

    @Autowired
    public TopicService(TopicRepository topicRepository) {
        this.topicRepository = topicRepository;
    }

    public List<Topic> findAll() {
        return topicRepository.findAll();
    }

    public Optional<Topic> findById(int id) {
        return topicRepository.findById(id);
    }

    public Topic save(TopicDTO topicDTO) {
        Topic topic = Topic.builder()
                .title(topicDTO.getTitle())
                .build();
        return topicRepository.save(topic);
    }
}
