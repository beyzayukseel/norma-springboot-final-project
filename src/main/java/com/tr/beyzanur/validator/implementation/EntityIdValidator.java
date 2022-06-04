package com.tr.beyzanur.validator.implementation;

import com.tr.beyzanur.exception.BaseValidationException;
import com.tr.beyzanur.exception.ValidationOperationException;
import com.tr.beyzanur.validator.Validator;
import org.springframework.stereotype.Component;


@Component
public class EntityIdValidator implements Validator<Long,String> {

    @Override
    public void validate(Long input, String name) throws BaseValidationException {
        if(input < 0) {
            throw new ValidationOperationException.IdNotValidator(name + " not found!");
        }
    }

}
