package com.example.sproject.Models;


import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.stereotype.Component;

import javax.persistence.Entity;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Objects;
import java.util.Set;

@Entity(name = "Client")
@Table
@Component
public class User {
    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    Long id;
    @Column(unique = true)
    String username;
    String password;
    String lastname;
    String firstname;

    @Lob
    private byte[] data;



    @OneToMany(mappedBy = "user",
            cascade = CascadeType.ALL)
    @JsonIgnore
    private Set<News> pages;

    public String getPassword() {
        return password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<News> getPages() {
        return pages;
    }

    public void setPages(Set<News> pages) {
        this.pages = pages;
    }

    public Set<Comment> getComments() {
        return comments;
    }

    public void setComments(Set<Comment> comments) {
        this.comments = comments;
    }
    @OneToMany(mappedBy = "userComment",
            cascade = CascadeType.ALL)

    private Set<Comment> comments;


    public User(Long id, String lastname, String firstname, byte[] data) {
        this.id = id;
        this.lastname = lastname;
        this.firstname = firstname;
        this.data = data;
    }

    public User(String username, String password, String lastname, String firstname) {
        this.username = username;
        this.password = password;
        this.lastname = lastname;
        this.firstname = firstname;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User user)) return false;
        return Objects.equals(getUsername(), user.getUsername()) && Objects.equals(getPassword(), user.getPassword()) && Objects.equals(getLastname(), user.getLastname()) && Objects.equals(getFirstname(), user.getFirstname());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getUsername(), getPassword(), getLastname(), getFirstname());
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", lastname='" + lastname + '\'' +
                ", firstname='" + firstname + '\'' +
                ", data=" + Arrays.toString(data) +
                ", pages=" + pages +
                ", comments=" + comments +
                '}';
    }

    public User() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }



    public byte[] getData() {
        return data;
    }

    public void setData(byte[] data) {
        this.data = data;
    }


}
