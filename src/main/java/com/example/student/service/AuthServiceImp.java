package com.example.student.service;

import com.example.student.dto.LoginRequest;
import com.example.student.dto.LoginResponse;
import com.example.student.dto.RegisterRequest;
import com.example.student.entity.User;
import com.example.student.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.example.student.security.JwtService;
import com.example.student.service.AuthService;

@Service
public class AuthServiceImp implements AuthService{

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    public AuthServiceImp(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtService jwtService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService=jwtService;
    }

    @Override
    public void register(RegisterRequest request){

        if(userRepository.findByUsername(request.username())
                         .isPresent()){

            throw new RuntimeException("Username already exists");
        }

        User user = new User();

        user.setUsername(request.username());
        user.setPassword(
                passwordEncoder.encode(request.password())
        );
        user.setRole("USER");

        userRepository.save(user);

    }

    @Override
    public LoginResponse login(LoginRequest request) {

        User user = userRepository
                .findByUsername(request.username())
                .orElseThrow(()-> new RuntimeException("Invalid username"));

        boolean passwordMatches = passwordEncoder.matches(request.password(),user.getPassword());

        if(!passwordMatches){
            throw new RuntimeException("Invalid password");
        }

        String token = jwtService.generateToken(user.getUsername(),user.getRole());

        return new LoginResponse(token);
    }
}
