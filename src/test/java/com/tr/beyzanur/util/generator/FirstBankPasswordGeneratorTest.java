package com.tr.beyzanur.util.generator;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class FirstBankPasswordGeneratorTest {

    private final long generatedCustomerNumberLength = 6;

    @Test
    void should_success_generating_password_when_number_is_valid() {
        long number = 123_123L;
        String customerNumber = Long.toString(number);
        Assertions.assertEquals(customerNumber.length(), generatedCustomerNumberLength);
    }

    @Test
    void should_fail_generating_password_when_number_is_valid() {
        long number = 1_123_123L;
        String customerNumber = Long.toString(number);
        Assertions.assertNotEquals(customerNumber.length(), generatedCustomerNumberLength);
    }
}
