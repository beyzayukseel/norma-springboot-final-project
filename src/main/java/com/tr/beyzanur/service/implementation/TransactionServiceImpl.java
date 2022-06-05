package com.tr.beyzanur.service.implementation;

import com.tr.beyzanur.converter.AccountConverter;
import com.tr.beyzanur.converter.CardConverter;
import com.tr.beyzanur.converter.TransactionConverter;
import com.tr.beyzanur.dto.request.CreateTransactionDto;
import com.tr.beyzanur.dto.response.TransactionResponseDto;
import com.tr.beyzanur.exception.ServiceOperationException;
import com.tr.beyzanur.exchange.Exchange;
import com.tr.beyzanur.exchange.ExchangeDto;
import com.tr.beyzanur.model.Account;
import com.tr.beyzanur.model.Card;
import com.tr.beyzanur.model.Transaction;
import com.tr.beyzanur.model.enums.AccountStatus;
import com.tr.beyzanur.model.enums.CardType;
import com.tr.beyzanur.model.enums.Currency;
import com.tr.beyzanur.model.enums.TransactionType;
import com.tr.beyzanur.repository.TransactionRepository;
import com.tr.beyzanur.service.AccountService;
import com.tr.beyzanur.service.CardService;
import com.tr.beyzanur.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class TransactionServiceImpl implements TransactionService {

    private final TransactionRepository transactionRepository;
    private final TransactionConverter transactionConverter;
    private final AccountService accountService;
    private final AccountConverter accountConverter;
    private final CardService cardService;
    private final CardConverter cardConverter;
    private final Exchange exchange;

    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED)
    public void createTransaction(CreateTransactionDto createTransactionDto) {

        Boolean checkSourceNumber = accountService.existAccountById(createTransactionDto.getAccountResponseDto().getId());
        Boolean checkDestinationNumber = accountService.exitsByIban(createTransactionDto.getDestinationIbanNumber());

        if (!(checkSourceNumber)) {
            throw new ServiceOperationException.NotFoundException("Not Found Source Account");
        }
        if (checkDestinationNumber) {
            throw new ServiceOperationException.NotFoundException("Not Found Destination Iban");
        }

        Account destinationAccount = accountService.findByIban(createTransactionDto.getDestinationIbanNumber());

        if (destinationAccount.getAccountType() != createTransactionDto.getAccountResponseDto().getAccountType()) {
            accountService.checkAccountsCustomerIdsAreMatch(createTransactionDto.getAccountResponseDto().getIban(),
                    destinationAccount.getIban());
        }

        Account sourceAccount = accountConverter.convertToEntityFromResponse(createTransactionDto.getAccountResponseDto());

        moneyTransfer(sourceAccount, destinationAccount, createTransactionDto.getQuantity());

        transactionRepository.save(transactionConverter.convertToEntity(createTransactionDto));
    }

    private void moneyTransfer(Account sourceAccount, Account destinationAccount, BigDecimal moneyTransfer) {
        BigDecimal sourceAccountBalance = sourceAccount.getBalance();
        int checkMoneyQuantityIsEnough = sourceAccountBalance.compareTo(moneyTransfer);
        if (checkMoneyQuantityIsEnough == -1) {
            throw new ServiceOperationException.NotValidException("Not enough money in your account!");
        }

        Currency sourceAccountCurrency = sourceAccount.getCurrency();
        Currency destinationAccountCurrency = destinationAccount.getCurrency();

        if (sourceAccountCurrency == destinationAccountCurrency){
            sourceAccount.setBalance(sourceAccountBalance.subtract(moneyTransfer));
            destinationAccount.setBalance(moneyTransfer.add(moneyTransfer));
        }else {
            ResponseEntity <ExchangeDto> exchangeModel = exchange.getExchange(sourceAccount.getCurrency().toString());
            sourceAccount.setBalance(sourceAccountBalance.subtract(moneyTransfer));
            BigDecimal exchangeQuantity = moneyTransfer.multiply(exchangeModel.getBody().getRates()
                    .exchangeValueByCurrency(destinationAccountCurrency.toString()));

            destinationAccount.setBalance(destinationAccount.getBalance().add(exchangeQuantity));
            accountService.saveAccount(destinationAccount);
            accountService.saveAccount(sourceAccount);
        }
    }

    @Override
    @Transactional
    public void payDebtFromCardAccounts(Long id, BigDecimal debt) {

        Card card = cardConverter.convertToResponseDto(cardService.getCardById(id));
        List<Account> accountList = card.getAccountList();
        BigDecimal checkCardDebt = card.getDebt();
        BigDecimal cardDebt = card.getDebt();

        for (Account account : accountList) {
            AccountStatus accountStatus = account.getAccountStatus();
            BigDecimal accountBalance = account.getBalance();

            int compareBalanceWithDebt = accountBalance.compareTo(debt);
            if (accountStatus == AccountStatus.APPROVED && !(compareBalanceWithDebt == -1)){
                  cardDebt.equals(cardDebt.subtract(debt));
                  accountBalance.equals(accountBalance.subtract(debt));
                  card.setDebt(cardDebt);
                  account.setBalance(accountBalance);
                  cardService.saveCard(card);
                  accountService.saveAccount(account);
                  break;
            }
        }

        if (cardDebt == checkCardDebt){
            throw new ServiceOperationException.NotValidException("Accounts linked to your credit card do " +
                    "not have the amount you want to pay.");
        }
    }

    @Override
    @Transactional
    public void payDebtFromExternalAccount(Long cardId, BigDecimal money, UUID externalAccountIban) {

        Card card = cardConverter.convertToResponseDto(cardService.getCardById(cardId));
        CardType cardType = card.getCardType();
        BigDecimal debt = card.getDebt();

        if (!(cardType == CardType.CREDIT)) {
            throw new ServiceOperationException.NotValidException("Card Type is not valid!");
        }

        if (debt.equals(BigDecimal.ZERO)) {
            throw new ServiceOperationException.NotFoundException("You have no unpaid debt!");
        }

        if (money.compareTo(debt) == 1){
            throw new ServiceOperationException.NotValidException("The amount you have entered to make the payment is " +
                    "higher than the debt");
        }

        // i have no services to control external accounts, i assume all of them valid

        card.setDebt(debt.subtract(money));
        cardService.saveCard(card);
    }

    @Transactional
    @Override
    public List<TransactionResponseDto> findAccountTransactions(Long accountId) {
        return transactionRepository.findTransactionByAccount_Id(accountId).stream()
                .map(transactionConverter::convertToDto)
                .collect(Collectors.toList());
    }


    @Override
    public TransactionResponseDto findTransaction(Long transactionId) {
        Transaction transaction = transactionRepository.findById(transactionId).orElseThrow();
        return transactionConverter.convertToDto(transaction);
    }


    @Override
    public List<TransactionResponseDto> findTransactionByDate(LocalDate date) {
        List<Transaction> transactions = transactionRepository.findByCreatedDate(date);
        return transactions.stream()
                .map(transactionConverter::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<TransactionResponseDto> findTransactionsByPeriod(LocalDate dateFrom, LocalDate dateTo) {
        List<Transaction> transactions = transactionRepository.findTransactionsByPeriod(dateFrom, dateTo);
        return transactions.stream()
                .map(transactionConverter::convertToDto)
                .collect(Collectors.toList());
    }
}
