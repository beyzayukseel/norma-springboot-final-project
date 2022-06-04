package com.tr.beyzanur.validator.implementation;

import com.tr.beyzanur.dto.request.CreateCardRequest;
import com.tr.beyzanur.exception.BaseValidationException;
import com.tr.beyzanur.exception.ValidationOperationException;
import com.tr.beyzanur.validator.Validator;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Objects;

@Component
public class CreateCardValidator implements Validator<CreateCardRequest, LocalDate> {

    @Override
    public void validate(CreateCardRequest createCardRequest, LocalDate name) throws BaseValidationException {
        if (Objects.isNull(createCardRequest)) {
            throw new ValidationOperationException.CanNotBeNullException("Card can not be null");
        }
        if (Objects.isNull(createCardRequest.getCustomerResponseDto().getId())) {
            throw new ValidationOperationException.CanNotBeNullException("Customer id can not be null");
        }
        if (createCardRequest.getCardType() == null) {
            throw new ValidationOperationException.NotValidException("Card type can not be null");
        }
    }
}

