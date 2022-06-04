package com.tr.beyzanur.dto.request;

import com.tr.beyzanur.dto.response.CustomerResponseDto;
import com.tr.beyzanur.model.Customer;
import com.tr.beyzanur.model.enums.AccountStatus;
import com.tr.beyzanur.model.enums.AccountType;
import com.tr.beyzanur.model.enums.Currency;
import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;
import java.time.LocalDate;

@Setter
@Getter
public class AccountRequestDto {
    private String accountNumber;
    private BigDecimal balance= BigDecimal.ZERO;
    private LocalDate createdDate;
    private LocalDate modifiedDate;
    private AccountType accountType;
    private Currency currency;
    private CustomerResponseDto customerResponseDto;
    private Boolean isDeleted;
    private AccountStatus accountStatus;
    private CreateCardRequest createCardRequest;
}
