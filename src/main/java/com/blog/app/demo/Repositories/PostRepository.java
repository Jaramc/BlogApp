package com.blog.app.demo.Repositories;


import com.blog.app.demo.Models.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {
}
