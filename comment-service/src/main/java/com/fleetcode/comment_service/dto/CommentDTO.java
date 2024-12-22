package com.fleetcode.comment_service.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommentDTO {
    private long userId;
    private long questionId;
    private String comment;
}
