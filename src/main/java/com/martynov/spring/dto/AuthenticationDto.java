package com.martynov.spring.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public class AuthenticationDto {
    @NotEmpty(message = "Имя не должно быть пустым")
    @Size(min = 2,max = 100,message = "Некорректное имя")
    private String username;
    private String password;

    public AuthenticationDto() {
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
}
