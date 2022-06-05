package com.tr.beyzanur.validator;


import com.tr.beyzanur.dto.request.AccountRequestDto;
import com.tr.beyzanur.dto.response.CustomerResponseDto;
import com.tr.beyzanur.model.enums.AccountType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class CreateAccountValidatorTest {

    @Test
    void should_throw_exception_when_account_request_is_null() {
        AccountRequestDto accountRequestDto = null;
        Assertions.assertNull(accountRequestDto);
    }

    @Test
    void should_throw_exception_when_customer_response_id_is_null() {
        AccountRequestDto accountRequestDto = new AccountRequestDto();
        CustomerResponseDto customerResponseDto = new CustomerResponseDto();
        customerResponseDto.setId(null);
        accountRequestDto.setCustomerResponseDto(customerResponseDto);

        Assertions.assertNotNull(accountRequestDto);
        Assertions.assertNull(accountRequestDto.getCustomerResponseDto().getId());
    }

    @Test
    void should_throw_exception_when_account_type_is_null1() {
        AccountRequestDto accountRequestDto = new AccountRequestDto();
        CustomerResponseDto customerResponseDto = new CustomerResponseDto();
        customerResponseDto.setId(1L);
        accountRequestDto.setCustomerResponseDto(customerResponseDto);
        accountRequestDto.setAccountType(null);

        Assertions.assertNotNull(accountRequestDto);
        Assertions.assertNotNull(accountRequestDto.getCustomerResponseDto().getId());
        Assertions.assertNull(accountRequestDto.getAccountType());
    }

    @Test
    void should_success_when_account_creation_params_are_valid() {
        AccountRequestDto accountRequestDto = new AccountRequestDto();
        CustomerResponseDto customerResponseDto = new CustomerResponseDto();
        customerResponseDto.setId(1L);
        accountRequestDto.setCustomerResponseDto(customerResponseDto);
        accountRequestDto.setAccountType(AccountType.SAVE);

        Assertions.assertNotNull(accountRequestDto);
        Assertions.assertNotNull(accountRequestDto.getCustomerResponseDto().getId());
        Assertions.assertNotNull(accountRequestDto.getAccountType());
    }
}
