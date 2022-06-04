package com.tr.beyzanur.dto.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CustomerResponseDto {
    private Long id;
    private String customerNumber;
    private Boolean isDeleted;
    private String email;
    private CustomerAddressResponseDto customerAddressResponseDto;
}
