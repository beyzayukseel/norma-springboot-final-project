package com.tr.beyzanur.dto.request;

import com.tr.beyzanur.dto.response.AccountResponseDto;
import com.tr.beyzanur.model.Account;
import com.tr.beyzanur.model.enums.TransactionType;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Setter
@Getter
public class CreateTransactionDto {

    private LocalDate createdDate;
    private UUID sourceIbanNumber;
    private UUID destinationIbanNumber;
    private BigDecimal quantity;
    private TransactionType transactionType;
    private AccountResponseDto accountResponseDto;
}
