package com.tr.beyzanur.dto.request;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.CascadeType;
import javax.persistence.OneToOne;
import java.util.Set;

@Setter
@Getter
public class RegisterRequest {

    private String username;
    private String surname;
    private String email;
    private Set<String> role;
    private String password;
    private String identifyNumber;
    private String phoneNumber;

    @OneToOne(cascade = CascadeType.PERSIST)
    private CreateUserAddressDto createUserAddressDto;
}
