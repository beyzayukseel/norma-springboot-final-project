package com.tr.beyzanur.util.generator;

public class NumberGenerator {

    public static String randomNumber() {
        Long number = (long) Math.floor(Math.random() * 9_000_000_000L) + 1_000_000_000L;
        String accountNumber =number.toString();
        return accountNumber;
    }
}
