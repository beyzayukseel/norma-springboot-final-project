package com.tr.beyzanur.dto.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CustomerAddressResponseDto {
    private String phoneNumber;
    private String country;
    private String city;
    private String postalCode;
    private String description;
}
