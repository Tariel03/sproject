package com.example.sproject.Dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;


public class AuthenticationDTO {
    @NotEmpty(message = "Имя не должно быть пустым")
    @Size(min = 2, max = 100, message = "Имя должно быть от 2 до 100 символов")
    private String username;

    @Size(min = 6, max = 100, message = "Пароль должен быть от 6 до 1000 символов")
    private String password;

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
}
