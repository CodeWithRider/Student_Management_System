package com.example.student.dto;

import jakarta.validation.constraints.NotBlank;

public record RegisterRequest(  //record class automatically provide accessor/constructor

        @NotBlank(message = "Username is required")
        String username,

        @NotBlank(message = "Password is required")
        String password

) {
}