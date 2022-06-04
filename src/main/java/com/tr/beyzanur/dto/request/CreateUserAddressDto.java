package com.tr.beyzanur.dto.request;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CreateUserAddressDto {
    private String phoneNumber;
    private String country;
    private String city;
    private String postalCode;
    private String description;
}
