package com.example.sproject.Services;

import com.example.sproject.Models.Comment;
import com.example.sproject.Repositories.CommentRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class CommentServiceTest {

    @Mock
    CommentService commentService;
    @Mock
    CommentRepository commentRepository;
    @Mock
    RegistrationService registrationService;
    AutoCloseable autoCloseable;

    @BeforeEach
    void setUp() {
        autoCloseable = MockitoAnnotations.openMocks(this);
       commentService = new CommentService(commentRepository, registrationService);
    }


    @Test
    void writeComment() {
        Comment comment = new Comment("Tariel is GOAT");

        commentService.writeComment(comment);

        ArgumentCaptor<Comment> commentArgumentCaptor = ArgumentCaptor.forClass(Comment.class);

        verify(commentRepository).save(commentArgumentCaptor.capture());
       Comment value = commentArgumentCaptor.getValue();
        assertThat(value).isEqualTo(comment);
    }

    @Test
    void findById() {
        Comment comment = commentService.findById(1L);
        verify(commentRepository).findById(1L);
    }

    @Test
    void findByInitial() {
        Comment comment = new Comment("Tariel is GOAT");
        List<Comment> commentList = commentService.findByInitial(comment);
         verify(commentRepository).findCommentsByInitialComment(comment);
    }

    @Test
    void allComments() {
        commentService.allComments();
        verify(commentRepository).findAll();
    }
}