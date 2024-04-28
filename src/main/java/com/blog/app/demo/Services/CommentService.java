package com.blog.app.demo.Services;

import com.blog.app.demo.Models.Comment;
import com.blog.app.demo.Models.Post;
import com.blog.app.demo.Models.User;
import com.blog.app.demo.Repositories.CommentRepository;
import com.blog.app.demo.Repositories.PostRepository;
import com.blog.app.demo.Repositories.UserRepository;
import com.blog.app.demo.Requests.CommentRequest;
import com.blog.app.demo.Responses.CommentResponse;
import com.blog.app.demo.Responses.PostResponse;
import com.blog.app.demo.Responses.UserResponse;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final PostRepository postRepository;

    public CommentResponse createComment(Long postId, CommentRequest request) {
        String username = ((UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();
        User user = userRepository.findByUsername(username).orElseThrow(() -> new IllegalArgumentException("User not found"));
        Post post = postRepository.findById(postId).orElseThrow(() -> new IllegalArgumentException("Post not found"));

        Comment comment = Comment.builder()
                .text(request.getComment())
                .post(post)
                .user(user)
                .build();

        comment = commentRepository.save(comment);
        return buildCommentResponse(comment);
    }

    public List<CommentResponse> getAllCommentsByPostId(Long postId) {
        return commentRepository.findAllById(Collections.singleton(postId)).stream()
                .map(this::buildCommentResponse)
                .collect(Collectors.toList());
    }

    public Comment updateComment(Long commentId, CommentRequest request) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new IllegalArgumentException("Comment not found"));

        String username = ((UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();
        if (!comment.getUser().getUsername().equals(username)) {
            throw new SecurityException("Unauthorized to update this comment");
        }

        comment.setText(request.getComment());
        return commentRepository.save(comment);
    }

    public void deleteComment(Long commentId) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new IllegalArgumentException("Comment not found"));

        String username = ((UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();
        if (!comment.getUser().getUsername().equals(username)) {
            throw new SecurityException("Unauthorized to delete this comment");
        }

        commentRepository.delete(comment);
    }

    private CommentResponse buildCommentResponse(Comment comment) {
        UserResponse commentUserResponse = UserResponse.builder()
                .id(comment.getUser().getId())
                .username(comment.getUser().getUsername())
                .email(comment.getUser().getEmail())
                .firstName(comment.getUser().getFirstName())
                .lastName(comment.getUser().getLastName())
                .build();

        User postUser = comment.getPost().getUser();
        UserResponse postUserResponse = UserResponse.builder()
                .id(postUser.getId())
                .username(postUser.getUsername())
                .email(postUser.getEmail())
                .firstName(postUser.getFirstName())
                .lastName(postUser.getLastName())
                .build();

        PostResponse postResponse = PostResponse.builder()
                .id(comment.getPost().getId())
                .title(comment.getPost().getTitle())
                .content(comment.getPost().getContent())
                .user(postUserResponse)
                .build();

        return CommentResponse.builder()
                .id(comment.getId())
                .comment(comment.getText())
                .user(commentUserResponse)
                .post(postResponse)
                .build();
    }
}
