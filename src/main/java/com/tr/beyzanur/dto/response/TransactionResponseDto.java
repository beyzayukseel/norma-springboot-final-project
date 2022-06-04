package com.tr.beyzanur.dto.response;

import com.tr.beyzanur.dto.request.AccountRequestDto;
import com.tr.beyzanur.model.enums.TransactionType;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Setter
@Getter
public class TransactionResponseDto {

    private LocalDate createdDate;
    private String sourceIbanNumber;
    private String destinationIbanNumber;
    private BigDecimal quantity;
    private TransactionType transactionType;
    private AccountResponseDto accountResponseDto;
}
