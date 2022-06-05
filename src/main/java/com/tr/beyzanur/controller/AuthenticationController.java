package com.tr.beyzanur.controller;

import com.tr.beyzanur.dto.request.CreateUserDto;
import com.tr.beyzanur.dto.request.LoginRequest;
import com.tr.beyzanur.dto.request.PasswordDto;
import com.tr.beyzanur.dto.response.UserResponse;
import com.tr.beyzanur.service.UserService;
import com.tr.beyzanur.util.constant.MessageResponse;
import com.tr.beyzanur.service.implementation.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @PutMapping()
    public ResponseEntity<MessageResponse> changePassword(@Valid @RequestBody PasswordDto passwordDto) {
        userDetails.changePassword(passwordDto);
        return ResponseEntity.status(HttpStatus.OK).body(new MessageResponse("User password changed successfully!"));
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<MessageResponse> delete(@Valid @PathVariable Long userId) {
        userDetails.deleteUser(userId);
        return ResponseEntity.status(HttpStatus.OK).body(new MessageResponse("User deleted successfully!"));
    }
}
