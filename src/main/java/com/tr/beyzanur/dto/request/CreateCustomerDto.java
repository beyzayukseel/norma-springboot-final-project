package com.tr.beyzanur.dto.request;

import com.tr.beyzanur.dto.response.CustomerAddressResponseDto;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CreateCustomerDto {
    private String customerNumber;
    private String email;
    private CustomerAddressResponseDto customerAddressResponseDto;
    private CreateUserDto createUserDto;
}
