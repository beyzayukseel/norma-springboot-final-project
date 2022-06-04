package com.tr.beyzanur.dto.request;

import com.tr.beyzanur.model.Role;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Setter
@Getter
public class UserRequestDto {

    private String username;
    private String email;
    private String phoneNumber;
    private String password;
    private Set<Role> roles;
}
