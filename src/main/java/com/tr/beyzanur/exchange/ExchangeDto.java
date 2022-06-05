package com.tr.beyzanur.exchange;

import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Setter
@Getter
public class ExchangeDto {

    private Rate rates;
    private String base;
    private Boolean success;
    private Timestamp timestamp;
}
