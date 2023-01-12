package com.example.sproject.Controllers;

import com.example.sproject.Exceptions.NewsException;
import com.example.sproject.Models.Comment;
import com.example.sproject.Services.CommentService;
import com.example.sproject.Services.NewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/comments")

public class CommentController {
    CommentService commentService;
    NewsService newsService;
    @Autowired
    public CommentController(CommentService commentService, NewsService newsService) {
        this.commentService = commentService;
        this.newsService = newsService;
    }

    @GetMapping
    public List<Comment> AllComments() {
        return commentService.comments();
    }
    @PostMapping("/write/comment/reply/{id}")
    public ResponseEntity<HttpStatus> writeReply(@PathVariable("id") Long id, @RequestBody Comment comment) {
        Comment initial = commentService.findById(id);
        comment.setInitialComment(initial);
        comment.setNews(initial.getNews());
        commentService.writeComment(comment);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @GetMapping("connected/comments/{id}")
    public List<Comment> connectedComments(@PathVariable("id") Long id) {
        Comment initial = commentService.findById(id);
        List<Comment> comments = commentService.findByInitial(initial);
        comments.add(0, initial);
        return comments;
    }
    @PostMapping("/write/comment/{id}")
    public ResponseEntity<HttpStatus> writeComment(@RequestBody @Valid Comment comment, BindingResult bindingResult, @PathVariable("id") Long id) {
        if (bindingResult.hasErrors()) {
            StringBuilder message = new StringBuilder();
            List<FieldError> fieldErrors = bindingResult.getFieldErrors();
            for (FieldError error : fieldErrors
            ) {
                message.append(error.getField()).append("-").append(error.getDefaultMessage());
            }
            throw new NewsException(message.toString());
        }
        comment.setNews(newsService.newsById(id));
        commentService.writeComment(comment);
        return ResponseEntity.ok(HttpStatus.OK);
    }
}
