package com.tr.beyzanur.validator;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class EntityIdValidatorTest {

    @Test
    void should_throw_exception_when_input_is_below_zero() {
        long input = -5;
        Assertions.assertFalse(input > 0);
    }


    @Test
    void should_success_when_input_is_above_zero() {
        long input = 5;
        Assertions.assertTrue(input > 0);
    }
}
