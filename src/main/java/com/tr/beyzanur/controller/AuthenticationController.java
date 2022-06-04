package com.tr.beyzanur.controller;

import com.tr.beyzanur.dto.request.CreateUserDto;
import com.tr.beyzanur.dto.request.LoginRequest;
import com.tr.beyzanur.dto.response.UserResponse;
import com.tr.beyzanur.util.constant.MessageResponse;
import com.tr.beyzanur.service.implementation.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import javax.validation.Valid;


@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/auth")
public class AuthenticationController {

    private final UserDetailsImpl userDetails;

    @PostMapping("/login")
    public ResponseEntity<UserResponse> login(@Valid @RequestBody LoginRequest loginRequest) {
        return ResponseEntity.status(HttpStatus.OK).body(userDetails.login(loginRequest));
    }

    @PostMapping("/register")
    public ResponseEntity<MessageResponse> register(@Valid @RequestBody CreateUserDto createUserDto) {
        userDetails.register(createUserDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(new MessageResponse("User registered successfully!"));
    }
}
