package com.example.sproject.Dto;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

public class UserDTO {
    @NotEmpty(message = "Имя не должно быть пустым")
    @Size(min = 2, max = 35, message = "Имя должно быть от 2 до 35 символов длиной")
    private String username;
    @NotEmpty
    @Size(min = 6, max = 100, message = "Пароль должен быть от 6 до 1000 символов")
    private String password;

    @NotEmpty
    private String firstname;
    @NotEmpty
    private String lastname;

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }


    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public UserDTO(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public UserDTO() {
    }
}
