package com.tr.beyzanur.service;

import com.tr.beyzanur.dto.request.CreateCardRequest;
import com.tr.beyzanur.dto.response.CardResponseDto;
import com.tr.beyzanur.model.Card;

import java.math.BigDecimal;
import java.util.List;

public interface CardService {

    CardResponseDto getCardById(Long id);

    CreateCardRequest demandCardCreation(CreateCardRequest createCardRequest);

    CardResponseDto approveCardCreationDemand(Long cardId);

    Boolean existsCardById(Long cardId);

    List<CardResponseDto> getCardsByCustomerId(Long userId);

    void addAccountToCard(Long accountId, Long cardId);

    void saveCard(Card card);

    Boolean checkCustomerHasCreditCardDebt(Long customerId);

    void removeAccountToCard(Long accountId, Long cardId);

    CardResponseDto denyCardCreationDemand(Long userId);

    void removeAllAccountsFromCard(Long cardId);

    CardResponseDto updateDebt(Long id, BigDecimal money) throws Exception;

    void deleteCard(Long id);

    void hardDeleteCard(Long id);
}
