package com.fleetcode.question_service.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class TopicDTO {
    private String title;

    public TopicDTO(Topic topic) {
        this.title = topic.getTitle();
    }
}
