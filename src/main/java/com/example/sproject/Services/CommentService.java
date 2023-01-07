package com.example.sproject.Services;

import com.example.sproject.Models.Comment;
import com.example.sproject.Models.News;
import com.example.sproject.Repositories.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
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
    public List<Comment> comments(){
        return commentRepository.findAll();
    }

}
