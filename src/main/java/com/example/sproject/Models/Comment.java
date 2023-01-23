package com.example.sproject.Models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.Objects;

@Entity
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    @Size(min = 2, max = 150)
    String message;

    @Override
    public String toString() {
        return "Comment{" +
                "id=" + id +
                ", message='" + message + '\'' +
                ", date=" + date +
                ", userComment=" + userComment +
                ", news=" + news +
                ", initialComment=" + initialComment +
                '}';
    }

    LocalDate date;

    public Comment(String message) {
        this.message = message;
        this.date = LocalDate.now();
    }

    @ManyToOne()
    @JoinColumn(name = "user_id")
    @JsonIgnore
    private User userComment;

    @ManyToOne()
    @JoinColumn(name = "news_id")
    @JsonIgnore
    private News news ;

    @ManyToOne()
    @JoinColumn(name = "Firstcomment_id")
    @JsonIgnore
    Comment initialComment;

    public Comment getInitialComment() {
        return initialComment;
    }

    public void setInitialComment(Comment initialComment) {
        this.initialComment = initialComment;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public User getUserComment() {
        return userComment;
    }

    public void setUserComment(User userComment) {
        this.userComment = userComment;
    }

    public News getNews() {
        return news;
    }

    public void setNews(News news) {
        this.news = news;
    }

    public Comment() {

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Comment comment)) return false;
        return Objects.equals(getMessage(), comment.getMessage()) && Objects.equals(getDate(), comment.getDate()) && Objects.equals(getUserComment(), comment.getUserComment()) && Objects.equals(getNews(), comment.getNews()) && Objects.equals(getInitialComment(), comment.getInitialComment());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getMessage(), getDate(), getUserComment(), getNews(), getInitialComment());
    }
}
