package com.tr.beyzanur.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class BalanceDto {
    private BigDecimal depositBalance = BigDecimal.ZERO;
    private BigDecimal savingBalance = BigDecimal.ZERO;
    private BigDecimal total = BigDecimal.ZERO;
}
