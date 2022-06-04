package com.tr.beyzanur.dto.response;
import com.tr.beyzanur.model.enums.AccountStatus;
import com.tr.beyzanur.model.enums.AccountType;
import com.tr.beyzanur.model.enums.Currency;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Setter
@Getter
public class AccountResponseDto {
    private Long id;
    private String accountNumber;
    private BigDecimal balance;
    private LocalDate createdDate;
    private LocalDate modifiedDate;
    private AccountType accountType;
    private Currency currency;
    private CustomerResponseDto customerResponseDto;
    private AccountStatus accountStatus;
}
