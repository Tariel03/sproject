package com.example.sproject.Models;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;
import java.util.Set;

@Entity
public class News {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    @NotEmpty(message = "write something")
    String header;
    @NotEmpty(message = "sm")
     @Size(min = 5,max = 45 , message = "This small text must be between 5 and 35 characters ")
    String small;
    @NotEmpty(message = "text can't be empty")
    String text;
    @NotEmpty(message = "enter genre")
    String genre;
    LocalDate localDate;


    public LocalDate getLocalDate() {
        return localDate;
    }

    public void setLocalDate(LocalDate localDate) {
        this.localDate = localDate;
    }

    @Lob
    private byte[] photo;
    private boolean liked;

    @ManyToOne()
    @JoinColumn(name = "user_id")
    @JsonIgnore
    private User user;


    @OneToMany(mappedBy = "news",
            cascade = CascadeType.ALL)
    @JsonIgnore
    private Set<Comment> comments;


    public News(byte[] photo, boolean liked, User user) {
        this.photo = photo;
        this.liked = liked;
        this.user = user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof News news)) return false;
        return isLiked() == news.isLiked() && Objects.equals(getHeader(), news.getHeader()) && Objects.equals(getSmall(), news.getSmall()) && Objects.equals(getText(), news.getText()) && Objects.equals(getGenre(), news.getGenre()) && Objects.equals(getUser(), news.getUser());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getHeader(), getSmall(), getText(), getGenre(), isLiked(), getUser());
    }


    @Override
    public String toString() {
        return "News{" +
                "id=" + id +
                ", header='" + header + '\'' +
                ", small='" + small + '\'' +
                ", text='" + text + '\'' +
                ", genre='" + genre + '\'' +
                ", localDate=" + localDate +
                ", photo=" + Arrays.toString(photo) +
                ", liked=" + liked +
                ", user=" + user +
                ", comments=" + comments +
                '}';
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public String getSmall() {
        return small;
    }

    public void setSmall(String small) {
        this.small = small;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public byte[] getPhoto() {
        return photo;
    }

    public void setPhoto(byte[] photo) {
        this.photo = photo;
    }

    public boolean isLiked() {
        return liked;
    }

    public void setLiked(boolean liked) {
        this.liked = liked;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Set<Comment> getComments() {
        return comments;
    }

    public void setComments(Set<Comment> comments) {
        this.comments = comments;
    }

    public News(Long id, String header, String small, String text, String genre, byte[] photo, boolean liked, User user, Set<Comment> comments) {
        this.id = id;
        this.header = header;
        this.small = small;
        this.text = text;
        this.genre = genre;
        this.photo = photo;
        this.liked = liked;
        this.user = user;
        this.comments = comments;
    }

    public News() {
        this.localDate = LocalDate.now();
    }
}
