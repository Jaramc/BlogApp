package com.blog.app.demo.Controllers;

import com.blog.app.demo.Models.Comment;
import com.blog.app.demo.Requests.CommentRequest;
import com.blog.app.demo.Responses.CommentResponse;
import com.blog.app.demo.Services.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/comments")
@RequiredArgsConstructor
public class CommentController {
    private final CommentService commentService;

    @PostMapping("/{postId}")
    public ResponseEntity<CommentResponse> createComment(@PathVariable Long postId, @RequestBody CommentRequest request) {
        CommentResponse response = commentService.createComment(postId, request);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/post/{postId}")
    public ResponseEntity<List<CommentResponse>> getAllCommentsByPostId(@PathVariable Long postId) {
        List<CommentResponse> responses = commentService.getAllCommentsByPostId(postId);
        return ResponseEntity.ok(responses);
    }

    @PutMapping("/{commentId}")
    public ResponseEntity<Comment> updateComment(@PathVariable Long commentId, @RequestBody CommentRequest request) {
        Comment response = commentService.updateComment(commentId, request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{commentId}")
    public ResponseEntity<Void> deleteComment(@PathVariable Long commentId) {
        commentService.deleteComment(commentId);
        return ResponseEntity.noContent().build();
    }
}
