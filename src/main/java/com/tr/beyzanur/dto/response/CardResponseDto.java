package com.tr.beyzanur.dto.response;

import com.tr.beyzanur.model.enums.CardStatus;
import com.tr.beyzanur.model.enums.CardType;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Setter
@Getter
public class CardResponseDto {
     Long id;
     private String cardNumber;
     private BigDecimal boundary=BigDecimal.ZERO;
     private BigDecimal debt = BigDecimal.ZERO;
     private Boolean isDeleted;
     private CardType cardType;
     private LocalDate createDate;
     private LocalDate paymentDate;
     private CardStatus cardStatus;

}
