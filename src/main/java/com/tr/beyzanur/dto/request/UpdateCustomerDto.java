package com.tr.beyzanur.dto.request;

import com.tr.beyzanur.dto.response.CustomerAddressResponseDto;
import com.tr.beyzanur.model.CustomerAddress;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateCustomerDto {
    private Long id ;
    private String customerNumber;
    private String email;
    private Boolean isDeleted;
    private CustomerAddressResponseDto customerAddressResponseDto;
    private CreateUserDto createUserDto;
}
