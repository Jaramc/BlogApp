package com.blog.app.demo.Responses;

import com.blog.app.demo.Models.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PostResponse {
    private int id;
    private String title;
    private String content;
    private UserResponse user;
}

