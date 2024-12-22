package com.fleetcode.comment_service.controller;

import com.fleetcode.comment_service.dto.CommentDTO;
import com.fleetcode.comment_service.model.Comment;
import com.fleetcode.comment_service.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/question")
public class CommentController {

    private final CommentService commentService;

    @Autowired
    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    // Get a specific comment by ID
    @GetMapping("/comments/{comment_id}")
    public ResponseEntity<Comment> getComment(@PathVariable Long comment_id) {
        Comment comment = commentService.getCommentById(comment_id);
        if (comment != null) {
            return ResponseEntity.ok(comment);  // Returns 200 OK if found
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();  // Returns 404 Not Found if not found
        }
    }

    // Get all comments for a specific question
    @GetMapping("/{question_id}/comments")
    public ResponseEntity<List<Comment>> getCommentByQuestion(@PathVariable Long question_id) {
        List<Comment> comments = commentService.getCommentsByQuestionId(question_id);
        if (comments.isEmpty()) {
            return ResponseEntity.noContent().build();  // Returns 204 No Content if list is empty
        } else {
            return ResponseEntity.ok(comments);  // Returns 200 OK with the list of comments
        }
    }

    // Get all comments for a specific question by a specific user
    @GetMapping("/{question_id}/user/{user_id}/comments")
    public ResponseEntity<List<Comment>> getCommentByUser(@PathVariable Long user_id, @PathVariable Long question_id) {
        List<Comment> comments = commentService.getCommentsByQuestionIdAndUserId(user_id, question_id);
        if (comments.isEmpty()) {
            return ResponseEntity.noContent().build();  // Returns 204 No Content if no comments are found
        } else {
            return ResponseEntity.ok(comments);  // Returns 200 OK with the list of comments
        }
    }

    // Add a new comment
    @PostMapping("/comments")
    public ResponseEntity<Comment> addComment(@RequestBody CommentDTO comment) {
        Comment savedComment = commentService.saveComment(comment);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedComment);  // Returns 201 Created with the saved comment
    }

    // Delete a comment by ID
    @DeleteMapping("/comments/{comment_id}")
    public ResponseEntity<Void> deleteComment(@PathVariable Long comment_id) {
        boolean deleted = commentService.deleteCommentById(comment_id);
        if (deleted) {
            return ResponseEntity.noContent().build();  // Returns 204 No Content if deletion was successful
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();  // Returns 404 Not Found if comment was not found
        }
    }

    @PutMapping("/comments/{comment_id}")
    public ResponseEntity<Comment> updateComment(@PathVariable Long comment_id, @RequestBody String comment) {
        try {
            Comment updatedComment = commentService.updateComment(comment_id, comment);
            return ResponseEntity.ok(updatedComment);  // Returns 200 OK with the updated comment
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();  // Returns 404 Not Found if comment is not found
        }
    }
}
