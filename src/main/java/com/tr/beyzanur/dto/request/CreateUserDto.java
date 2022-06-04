package com.tr.beyzanur.dto.request;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Setter
@Getter
public class CreateUserDto {

    private String identifyNumber;
    private String name;
    private String phoneNumber;
    private String email;
    private String password;
    private Set<String> roles;
}
