package com.example.sproject.Services;

import com.example.sproject.Models.Comment;
import com.example.sproject.Models.News;
import com.example.sproject.Repositories.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;

@Service
@Transactional
public class CommentService {
    CommentRepository commentRepository;
    RegistrationService registrationService;


    @Autowired
    public CommentService(CommentRepository commentRepository, RegistrationService registrationService) {
        this.commentRepository = commentRepository;
        this.registrationService = registrationService;
    }

    public void writeComment(Comment comment){
        comment.setDate(LocalDate.now());
        comment.setUserComment(registrationService.currentUser());
        commentRepository.save(comment);

    }
    public Comment findById(Long id){
        return commentRepository.findById(id).orElse(null);
    }
    public List<Comment> findByInitial(Comment comment){
        return commentRepository.findCommentsByInitialComment(comment);
    }

    public List<Comment> comments(News news){
        return commentRepository.findCommentsByNews(news);
    }

    public List<Comment>allComments(){
        return commentRepository.findAll();
    }

}
