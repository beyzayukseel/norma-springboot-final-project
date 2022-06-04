package com.tr.beyzanur.util.generator;

public class FirstBankPasswordGenerator {
    public static String firstBankPasswordGenerator() {
        Long number = (long) Math.floor(Math.random() * 900_000L) + 100_000L;
        String customerNumber =number.toString();
        return customerNumber;
    }
}
