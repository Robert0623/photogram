package com.hwoo.photogram.web.repository;

import com.hwoo.photogram.domain.comment.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {
}