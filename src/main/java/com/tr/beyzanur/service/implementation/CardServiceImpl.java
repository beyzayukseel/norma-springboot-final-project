package com.tr.beyzanur.service.implementation;

import com.tr.beyzanur.converter.AccountConverter;
import com.tr.beyzanur.converter.CardConverter;
import com.tr.beyzanur.dto.request.CreateCardRequest;
import com.tr.beyzanur.dto.response.CardResponseDto;
import com.tr.beyzanur.exception.ServiceOperationException;
import com.tr.beyzanur.model.Account;
import com.tr.beyzanur.model.Card;
import com.tr.beyzanur.model.enums.AccountStatus;
import com.tr.beyzanur.model.enums.AccountType;
import com.tr.beyzanur.model.enums.CardStatus;
import com.tr.beyzanur.model.enums.CardType;
import com.tr.beyzanur.repository.CardRepository;
import com.tr.beyzanur.service.AccountService;
import com.tr.beyzanur.service.CardService;
import com.tr.beyzanur.util.generator.NumberGenerator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
public class CardServiceImpl implements CardService {

    private final CardRepository cardRepository;
    private final CardConverter cardConverter;
    private final AccountService accountService;
    private final AccountConverter accountConverter;

    @Override
    public CardResponseDto getCardById(Long id) {
        Card card = cardRepository.findById(id)
                .orElseThrow(() -> new ServiceOperationException.NotFoundException("Card not found"));

        CardStatus cardStatus = card.getCardStatus();

        if (!(cardStatus == CardStatus.APPROVED)) {
            throw new ServiceOperationException.NotValidException("Account is not usable now");
        }

        log.info("Card ID -> {} date: {} getting", card.getId(), new Date());

        return cardConverter.convertToResponseDto(card);
    }

    @Override
    public CreateCardRequest demandCardCreation(CreateCardRequest createCardRequest) {
        Card card = new Card();
        card.setCardNumber(NumberGenerator.randomNumber());
        card.setCreateDate(LocalDate.now());
        card.setPaymentDate(LocalDate.now().plusMonths(1));
        card = cardRepository.save(cardConverter.convertToEntity(createCardRequest));
        log.info("Card ID -> {} date: {} saved", card.getId(), new Date());
        return cardConverter.convertToDto(card);
    }

    @Override
    public CardResponseDto approveCardCreationDemand(Long cardId) {
        Card card = new Card();
        card.setId(cardId);
        card.setCardStatus(CardStatus.APPROVED);
        cardRepository.save(card);
        log.info("Approved Card ID -> {} Creation Demand date: {} ", card.getId(), new Date());
        return cardConverter.convertToResponseDto(card);
    }

    @Override
    public void addAccountToCard(Long accountId, Long cardId) {
        Account account = accountConverter.
                convertToEntityFromResponse(accountService.getAccountById(accountId));
        account.setId(accountId);

        AccountType accountType = account.getAccountType();
        AccountStatus accountStatus = account.getAccountStatus();

        if (!(accountStatus == AccountStatus.APPROVED)) {
            throw new ServiceOperationException.NotValidException("Card is not active");
        }

        if (!(accountType == AccountType.DEPO)) {
            throw new ServiceOperationException.NotValidException("Account Type is not Valid");
        }

        Card card = cardRepository.getById(cardId);

        account.getCards().add(card);
        accountService.saveAccount(account);

        log.info("Added Account ID -> {} To Cart date: {} ", account.getId(), new Date());

    }

    @Override
    public void saveCard(Card card) {
        cardRepository.save(card);
    }

    @Override
    public Boolean checkCustomerHasCreditCardDebt(Long customerId) {
        List<Card> cardList = cardRepository.getCardsByCustomer_Id(customerId);
        for (Card card : cardList) {
            if (card.getCardType() == CardType.CREDIT) {
                if (card.getDebt().compareTo(BigDecimal.ZERO) == 1) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public void removeAccountToCard(Long accountId, Long cardId) {
        Account account = accountConverter.convertToEntityFromResponse(accountService.getAccountById(accountId));
        account.setId(accountId);

        Card card = cardRepository.getById(cardId);
        card.setId(cardId);
        card.getAccountList().remove(account);

        log.info("Removed Account ID -> {} To Cart date: {} ", account.getId(), new Date());
    }

    public List<CardResponseDto> getCardAccounts(Long cardId) {
        cardRepository.getCardByAccounts(cardId);
        return null;
    }

    @Override
    public CardResponseDto denyCardCreationDemand(Long cardId) {
        Card card = new Card();
        card.setId(cardId);
        card.setCardStatus(CardStatus.DENY);
        cardRepository.save(card);
        log.info("Deny Card ID -> {} Creation Demand date: {} ", card.getId(), new Date());
        return cardConverter.convertToResponseDto(card);
    }

    @Override
    public Boolean existsCardById(Long cardId) {
        return cardRepository.existsById(cardId);
    }

    @Override
    public List<CardResponseDto> getCardsByCustomerId(Long userId) {
        return cardRepository.getCardsByCustomer_Id(userId)
                .stream()
                .map(cardConverter::convertToResponseDto)
                .collect(Collectors.toList());
    }

    @Override
    public void removeAllAccountsFromCard(Long cardId) {
        Card card = cardRepository.findById(cardId).
                orElseThrow(() -> new ServiceOperationException.NotFoundException("Card not found"));

        List<Account> accountList = card.getAccountList();
        for (Account account : accountList) {
            card.getAccountList().remove(account);
        }
    }

    @Override
    @Transactional
    public CardResponseDto updateDebt(Long id, BigDecimal money) throws Exception {
        Card card = cardRepository.findById(id)
                .orElseThrow(() -> new ServiceOperationException.NotFoundException(" card not found"));

        CardType cardType = card.getCardType();

        if (!(cardType == CardType.CREDIT)) {
            throw new ServiceOperationException.NotValidException("this is not credit card");
        }

        int checkLimit = card.getBoundary().compareTo(card.getDebt());

        if (checkLimit == 1) {
            card.setDebt(card.getDebt().add(money));
            return cardConverter.convertToResponseDto(cardRepository.save(card));
        } else {
            throw new Exception("Limit not enough!");
        }
    }


    @Override
    public void deleteCard(Long id) {

        cardRepository.findById(id).orElseThrow(
                () -> new ServiceOperationException.NotFoundException("Account not found"));

        removeAllAccountsFromCard(id);
        log.info("Card ID -> {} date: {} deleted", id, new Date());

    }

    @Override
    public void hardDeleteCard(Long id) {
        log.info("Card ID -> {} date: {} hard deleted", id, new Date());
    }
}
