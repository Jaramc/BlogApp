package com.blog.app.demo.Services;

import com.blog.app.demo.Models.Post;
import com.blog.app.demo.Models.User;
import com.blog.app.demo.Repositories.PostRepository;
import com.blog.app.demo.Repositories.UserRepository;
import com.blog.app.demo.Requests.PostRequest;
import com.blog.app.demo.Responses.PostResponse;
import com.blog.app.demo.Responses.UserResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;
    private final UserRepository userRepository;

    public PostResponse createPost(PostRequest request) {
        String username = ((UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();
        User user = userRepository.findByUsername(username).orElseThrow(() -> new RuntimeException("User not found"));

        Post post = Post.builder()
                .title(request.getTitle())
                .content(request.getContent())
                .user(user)
                .build();

        post = postRepository.save(post);
        UserResponse userResponse = UserResponse.builder()
                .id(user.getId())
                .username(user.getUsername())
                .email(user.getEmail())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .build();

        return PostResponse.builder()
                .id(post.getId().intValue())
                .title(post.getTitle())
                .content(post.getContent())
                .user(userResponse)
                .build();
    }
}
