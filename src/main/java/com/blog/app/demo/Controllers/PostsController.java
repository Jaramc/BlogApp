package com.blog.app.demo.Controllers;

import com.blog.app.demo.Models.Post;
import com.blog.app.demo.Requests.PostRequest;
import com.blog.app.demo.Responses.PostResponse;
import com.blog.app.demo.Services.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/posts")
@RequiredArgsConstructor
public class PostsController {
    private final PostService postService;
    @PostMapping(value = "index")
    public String getPosts(){
        return "Hello World from posts";
    }
    @PostMapping("/create")
    public ResponseEntity<PostResponse> createPost(@RequestBody PostRequest request) {
        PostResponse response = postService.createPost(request);
        return ResponseEntity.ok(response);
    }
}