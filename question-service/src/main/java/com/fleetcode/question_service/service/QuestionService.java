package com.fleetcode.question_service.service;

import com.fleetcode.question_service.model.Question;
import com.fleetcode.question_service.model.QuestionDTO;
import com.fleetcode.question_service.model.Topic;
import com.fleetcode.question_service.model.TopicDTO;
import com.fleetcode.question_service.repository.QuestionRepository;
import com.fleetcode.question_service.repository.TopicRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class QuestionService {

    private final QuestionRepository questionRepository;
    private final TopicRepository topicRepository;

    @Autowired
    public QuestionService(QuestionRepository questionRepository, TopicRepository topicRepository) {
        this.questionRepository = questionRepository;
        this.topicRepository = topicRepository;
    }

    public List<Question> findAll() {
        return questionRepository.findAll();
    }

    public Optional<Question> findById(Long id) {
        return Optional.ofNullable(questionRepository.findById(id).orElse(null));
    }

    @Transactional
    public Optional<Question> createQuestion(QuestionDTO questionDTO) {
        List<Topic> topics = topicRepository.findByTitleIn(questionDTO.getTopics());
        Question newQuestion = Question.builder()
                .question(questionDTO.getQuestion())
                .difficulty(questionDTO.getDifficulty())
                .topics(topics)
                .build();

        return Optional.of(questionRepository.save(newQuestion));
    }

    public Optional<Question> updateQuestion(Long id, QuestionDTO questionDTO) {
        Question newQuestion = questionRepository.findById(id).orElse(null);
        assert newQuestion != null;
        List<Topic> topics = topicRepository.findByTitleIn(questionDTO.getTopics());
        newQuestion.setQuestion(questionDTO.getQuestion());
        newQuestion.setDifficulty(questionDTO.getDifficulty());
        newQuestion.setTopics(topics);
        return Optional.of(questionRepository.save(newQuestion));
    }

    public void deleteQuestion(Long id) {
        questionRepository.deleteById(id);
    }

    public List<Question> findQuestionsByTopics(List<TopicDTO> topicDTOS) {
        List<String> titles = topicDTOS.stream()
                .map(TopicDTO::getTitle)
                .collect(Collectors.toList());
        return questionRepository.findByTopicsIn(titles);
    }

    public List<Question> findQuestionsByDifficulty(String difficulty) {
        return  questionRepository.findByDifficulty(difficulty);
    }
}
