package com.tr.beyzanur.validator.implementation;

import com.tr.beyzanur.dto.request.AccountRequestDto;
import com.tr.beyzanur.exception.BaseValidationException;
import com.tr.beyzanur.exception.ValidationOperationException;
import com.tr.beyzanur.validator.Validator;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Objects;

@Component
public class CreateAccountValidator implements Validator<AccountRequestDto, LocalDate> {

    @Override
    public void validate(AccountRequestDto accountRequestDto, LocalDate name) throws BaseValidationException {
        if (Objects.isNull(accountRequestDto)) {
            throw new ValidationOperationException.CanNotBeNullException("Account can not be null");
        }
        if (Objects.isNull(accountRequestDto.getCustomerResponseDto().getId())) {
            throw new ValidationOperationException.CanNotBeNullException("Customer id can not be null");
        }
        if (accountRequestDto.getAccountType() == null) {
            throw new ValidationOperationException.NotValidException("Account type can not be null");
        }
    }
}