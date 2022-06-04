package com.tr.beyzanur.validator.implementation;

import com.tr.beyzanur.dto.request.CreateCustomerDto;
import com.tr.beyzanur.exception.BaseValidationException;
import com.tr.beyzanur.exception.ValidationOperationException;
import com.tr.beyzanur.validator.Validator;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.time.LocalDate;
import java.util.Objects;

@Component
public class CreateCustomerValidator implements Validator<CreateCustomerDto, LocalDate> {

    @Override
    public void validate(CreateCustomerDto createCustomerDto, LocalDate name) throws BaseValidationException {
        if (Objects.isNull(createCustomerDto)) {
            throw new ValidationOperationException.CanNotBeNullException("Customer can not be null or empty");
        }
        if (Objects.isNull(createCustomerDto.getCustomerAddressResponseDto())) {
            throw new ValidationOperationException.CanNotBeNullException("Customer address can not be null or empty");
        }
        if (Objects.isNull(createCustomerDto.getCreateUserDto())) {
            throw new ValidationOperationException.CanNotBeNullException("Customer address can not be null or empty");
        }
        if (!(StringUtils.hasLength(createCustomerDto.getEmail()))) {
            throw new ValidationOperationException.CanNotBeNullException("Customer email can not be null or empty");
        }
    }
}
