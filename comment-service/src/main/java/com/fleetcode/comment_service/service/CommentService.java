package com.fleetcode.comment_service.service;

import com.fleetcode.comment_service.dto.CommentDTO;
import com.fleetcode.comment_service.model.Comment;
import com.fleetcode.comment_service.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class CommentService {

    private final CommentRepository commentRepository;

    @Autowired
    public CommentService(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    public Comment saveComment(CommentDTO commentDTO) {
        Comment comment = Comment.builder()
                .userId(commentDTO.getUserId())
                .question(commentDTO.getQuestionId())
                .commentText(commentDTO.getComment())
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        return commentRepository.save(comment);
    }

    public Comment updateComment(Long commentId, String comment) {
        Optional<Comment> existingCommentOpt = commentRepository.findById(commentId);
        if (existingCommentOpt.isPresent()) {
            Comment existingComment = existingCommentOpt.get();
            existingComment.setCommentText(comment);  // Update the text
            existingComment.setUpdatedAt(LocalDateTime.now());         // Set update timestamp
            return commentRepository.save(existingComment);            // Save updated comment
        } else {
            throw new RuntimeException("Comment not found with id " + commentId);  // Handle not found
        }
    }

    public Comment getCommentById(Long id) {
        return commentRepository.findById(id).orElse(null);
    }

    public List<Comment> getCommentsByQuestionId(Long questionId) {
        return commentRepository.findByQuestion(questionId);
    }

    public boolean deleteCommentById(Long commentId) {
        if (commentRepository.existsById(commentId)) {
            commentRepository.deleteById(commentId);
            return true;
        } else {
            return false;
        }
    }

    public List<Comment> getCommentsByQuestionIdAndUserId(Long questionId, Long userId) {
        return commentRepository.findByUserIdAndQuestion(questionId, userId);
    }
}
