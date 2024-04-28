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
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;
    private final UserRepository userRepository;

    public PostResponse getPost(Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new RuntimeException("Post not found"));
        return buildPostResponse(post);
    }

    public List<PostResponse> getAllPosts() {
        List<Post> posts = postRepository.findAll();
        return posts.stream().map(this::buildPostResponse).collect(Collectors.toList());
    }

    public PostResponse updatePost(Long postId, PostRequest request) {
        Post post = postRepository.findById(postId).orElseThrow(() -> new RuntimeException("Post not found"));
        String currentUsername = ((UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();
        if (!post.getUser().getUsername().equals(currentUsername)) {
            throw new RuntimeException("Unauthorized attempt to edit a post");
        }
        post.setTitle(request.getTitle());
        post.setContent(request.getContent());
        post = postRepository.save(post);

        return buildPostResponse(post);
    }

    public void deletePost(Long postId) {
        Post post = postRepository.findById(postId).orElseThrow(() -> new RuntimeException("Post not found"));
        String currentUsername = ((UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();
        if (!post.getUser().getUsername().equals(currentUsername)) {
            throw new RuntimeException("Unauthorized attempt to delete a post");
        }
        postRepository.delete(post);
    }

    private PostResponse buildPostResponse(Post post) {
        User user = post.getUser();
        UserResponse userResponse = UserResponse.builder()
                .id(user.getId())
                .username(user.getUsername())
                .email(user.getEmail())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .build();

        return PostResponse.builder()
                .id(post.getId())
                .title(post.getTitle())
                .content(post.getContent())
                .user(userResponse)
                .build();
    }

    public PostResponse createPost(PostRequest request) {
        String username = ((UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();
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
                .id(post.getId())
                .title(post.getTitle())
                .content(post.getContent())
                .user(userResponse)
                .build();
    }
}
