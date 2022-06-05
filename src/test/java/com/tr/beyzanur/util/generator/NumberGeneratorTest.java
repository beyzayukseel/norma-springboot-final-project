package com.tr.beyzanur.util.generator;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class NumberGeneratorTest {

    private final long generatedAccountNumberLength = 10;

    @Test
    void should_success_generating_account_number_when_number_is_valid() {
        long number = 1_234_567_890L;
        String accountNumber = Long.toString(number);
        Assertions.assertEquals(accountNumber.length(), generatedAccountNumberLength);
    }

    @Test
    void should_fail_generating_account_number_when_number_is_valid() {
        long number = 12_345_678_900L;
        String accountNumber = Long.toString(number);
        Assertions.assertNotEquals(accountNumber.length(), generatedAccountNumberLength);
    }
}
