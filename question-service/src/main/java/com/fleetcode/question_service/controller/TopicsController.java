package com.fleetcode.question_service.controller;

import com.fleetcode.question_service.model.Topic;
import com.fleetcode.question_service.model.TopicDTO;
import com.fleetcode.question_service.service.TopicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/topic")
public class TopicsController {
    private final TopicService topicService;

    @Autowired
    public TopicsController(TopicService topicService) {
        this.topicService = topicService;
    }

    @GetMapping("/all")
    @Transactional
    public List<TopicDTO> getTopics() {
        return topicService.findAll()
                .stream()
                .map(TopicDTO::new)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<TopicDTO> getTopic(@PathVariable int id) {
        return topicService.findById(id)
                .map(topic -> TopicDTO.builder()
                        .title(topic.getTitle())
                        .build())
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }


    @PostMapping()
    public Topic createTopic(@RequestBody TopicDTO topicDTO) {
        return topicService.save(topicDTO);
    }
}
