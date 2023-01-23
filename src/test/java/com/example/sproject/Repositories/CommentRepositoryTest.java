package com.example.sproject.Repositories;

import com.example.sproject.Dto.UserDTO;
import com.example.sproject.Models.Comment;
import com.example.sproject.Models.News;
import com.example.sproject.Models.User;
import org.aspectj.weaver.loadtime.definition.Definition;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
@DataJpaTest
class CommentRepositoryTest {
    @Autowired
    CommentRepository commentRepository;
    @Autowired
    ClientRepository clientRepository;
    @Autowired
    private NewsRepository newsRepository;

    private User getUser() {
        User user = new User("tariel", "nurlanobivc", "Akmatov", "Tariel");
        clientRepository.save(user);
        return user;
    }


    @Test
    void findCommentsByNews() {
        User user = getUser();
        News news = new News(1L, "iehasidhsadhsahd", "adsdhsjahdhaks", "asdasdoiashd", "anime",null,false,user,null);
        newsRepository.save(news);
        Comment comment = new Comment();
        comment.setUserComment(user);
        comment.setNews(news);
        comment.setDate(LocalDate.now());
        commentRepository.save(comment);
        assertEquals(comment, commentRepository.findCommentsByNews(news).get(0));

    }

    @Test
    void findCommentsByInitialComment() {
        User user = getUser();
        Comment initialComment = new Comment("Enooototot");
        commentRepository.save(initialComment);
        Comment comment = new Comment("No way you said that bro!");
        comment.setInitialComment(initialComment);
        commentRepository.save(comment);
        assertEquals(comment, commentRepository.findCommentsByInitialComment(initialComment).get(0));

    }


}