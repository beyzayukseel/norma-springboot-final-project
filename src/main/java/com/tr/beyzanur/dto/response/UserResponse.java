package com.tr.beyzanur.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@Setter
@Getter
public class UserResponse {

    private String token;
    private String username;
    private String email;
    private List<String> roles;
}
