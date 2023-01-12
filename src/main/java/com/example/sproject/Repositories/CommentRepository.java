package com.example.sproject.Repositories;

import com.example.sproject.Models.Comment;
import com.example.sproject.Models.News;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment,Long> {
    List<Comment>findCommentsByNews(News news);
    List<Comment>findCommentsByInitialComment(Comment comment);

}
