package com.tr.beyzanur.dto.request;

import com.tr.beyzanur.dto.response.AccountResponseDto;
import com.tr.beyzanur.dto.response.CustomerResponseDto;
import com.tr.beyzanur.model.Account;
import com.tr.beyzanur.model.Customer;
import com.tr.beyzanur.model.enums.CardStatus;
import com.tr.beyzanur.model.enums.CardType;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Setter
@Getter
public class CreateCardRequest {
    private String cardNumber;
    private BigDecimal boundary=BigDecimal.ZERO;
    private BigDecimal debt = BigDecimal.ZERO;
    private Boolean isDeleted;
    private CardType cardType;
    private CardStatus cardStatus;
    private AccountResponseDto accountResponseDto;
    private CustomerResponseDto customerResponseDto;
}
