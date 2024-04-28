package com.blog.app.demo.Controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class PostsController {
    @PostMapping(value = "post")
    public String getPosts(){
        return "Hello World from posts";
    }
}