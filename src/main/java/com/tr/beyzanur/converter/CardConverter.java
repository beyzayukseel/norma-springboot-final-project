package com.tr.beyzanur.converter;

import com.tr.beyzanur.dto.request.CreateCardRequest;
import com.tr.beyzanur.dto.response.CardResponseDto;
import com.tr.beyzanur.model.Card;
import com.tr.beyzanur.model.enums.AccountStatus;
import com.tr.beyzanur.model.enums.CardStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@RequiredArgsConstructor
@Component
public class CardConverter {

    private final CustomerConverter customerConverter;

    public CreateCardRequest convertToDto(Card card) {
        CreateCardRequest createCardRequest = new CreateCardRequest();
        if (card != null){
            BeanUtils.copyProperties(card,createCardRequest);
        }
        return createCardRequest;
    }

    public Card convertToEntity(CreateCardRequest cardRequestDto) {
        Card card = new Card();
        card.setCardStatus(CardStatus.PENDING);

        AccountStatus accountStatus = cardRequestDto.getAccountResponseDto().getAccountStatus();

        if (accountStatus == AccountStatus.APPROVED){
            card.setDebt(BigDecimal.ZERO);
        }else {
            card.setDebt(BigDecimal.ZERO);
        }

        card.setIsDeleted(false);
        card.setCardNumber(cardRequestDto.getCardNumber());
        card.setCardType(cardRequestDto.getCardType());
        card.setBoundary(BigDecimal.ZERO);
        card.setIsDeleted(Boolean.FALSE);
        card.setCustomer(customerConverter.convertToEntity(cardRequestDto.getCustomerResponseDto()));
        return card;
    }

    public CardResponseDto convertToResponseDto(Card entity) {
        CardResponseDto cardResponseDto = new CardResponseDto();
        if (entity != null) {
            BeanUtils.copyProperties(entity, cardResponseDto);
        }
        return cardResponseDto;
    }

    public Card convertToResponseDto(CardResponseDto dto) {
        Card card = new Card();
        if (dto != null) {
            BeanUtils.copyProperties(dto, card);
        }
        return card;
    }
}
