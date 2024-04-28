package com.blog.app.demo.Responses;

import com.blog.app.demo.Models.Post;
import com.blog.app.demo.Models.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CommentResponse {
    private Long id;
    private String comment;
    private PostResponse post;
    private UserResponse user;
}

