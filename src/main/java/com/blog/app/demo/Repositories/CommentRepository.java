package com.blog.app.demo.Repositories;

import com.blog.app.demo.Models.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {
}
