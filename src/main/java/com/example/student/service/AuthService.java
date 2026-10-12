package com.example.student.service;

import com.example.student.dto.LoginRequest;
import com.example.student.dto.LoginResponse;
import com.example.student.dto.RegisterRequest;

public interface AuthService {

    void register(RegisterRequest request);

    LoginResponse login(LoginRequest request);
}