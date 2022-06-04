package com.tr.beyzanur.service;

import com.tr.beyzanur.dto.request.CreateCardRequest;
import com.tr.beyzanur.dto.response.CardResponseDto;
import java.util.List;

public interface CardService {

    CardResponseDto getCardById(Long id);

    CreateCardRequest demandCardCreation(CreateCardRequest createCardRequest);

    CardResponseDto approveCardCreationDemand(Long cardId);

    Boolean existsCardById(Long cardId);

    List<CardResponseDto> getCardsByCustomerId(Long userId);

    void addAccountToCard(Long accountId, Long cardId);

    Boolean checkCustomerHasCreditCardDebt(Long customerId);

    void removeAccountToCard(Long accountId, Long cardId);

    CardResponseDto denyCardCreationDemand(Long userId);

    void deleteCard(Long id);

    void hardDeleteCard(Long id);
}
