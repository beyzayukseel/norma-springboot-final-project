package com.tr.beyzanur.validator;

import com.tr.beyzanur.exception.BaseValidationException;

public interface Validator<T,K> {
    void validate(T input, K name) throws BaseValidationException;
}
