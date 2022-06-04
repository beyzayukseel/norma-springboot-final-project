package com.tr.beyzanur.service.implementation;

import com.tr.beyzanur.converter.TransactionConverter;
import com.tr.beyzanur.dto.request.CreateTransactionDto;
import com.tr.beyzanur.dto.response.TransactionResponseDto;
import com.tr.beyzanur.exception.ServiceOperationException;
import com.tr.beyzanur.model.Account;
import com.tr.beyzanur.model.Transaction;
import com.tr.beyzanur.repository.TransactionRepository;
import com.tr.beyzanur.service.AccountService;
import com.tr.beyzanur.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;


import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class TransactionServiceImpl implements TransactionService {

    private final TransactionRepository transactionRepository;
    private final TransactionConverter transactionConverter;
    private final AccountService accountService;

    @Transactional(isolation = Isolation.READ_COMMITTED)
    public void createTransaction(CreateTransactionDto createTransactionDto) {
        if (accountService.exitsByIban(createTransactionDto.getSourceIbanNumber())) {
            Account sourceAccount = accountService.findByIban(createTransactionDto.getSourceIbanNumber());

            int checkBalance = sourceAccount.getBalance().compareTo(createTransactionDto.getQuantity());

            if (checkBalance == -1) {
                throw new ServiceOperationException.NotValidException("Not enough money for transaction");
            } else {
                BigDecimal sourceAccountSum = sourceAccount.getBalance().subtract(createTransactionDto.getQuantity());
                sourceAccount.setBalance(sourceAccountSum);
                accountService.saveAccount(sourceAccount);
            }
        }
        if (accountService.exitsByIban(createTransactionDto.getDestinationIbanNumber())) {
            Account destinationAccount = accountService.findByIban(createTransactionDto.getDestinationIbanNumber());
            BigDecimal destinationAccountSum = destinationAccount.getBalance().add(createTransactionDto.getQuantity());
            destinationAccount.setBalance(destinationAccountSum);
            accountService.saveAccount(destinationAccount);
        }
        transactionRepository.save(transactionConverter.convertToEntity(createTransactionDto));
    }

    @Transactional
    @Override
    public List<TransactionResponseDto> findAccountTransactions(Long accountId) {
         return transactionRepository.findTransactionByAccount_Id(accountId).stream()
                 .map(transactionConverter::convertToDto)
                 .collect(Collectors.toList());
    }

    @Transactional
    @Override
    public TransactionResponseDto findTransaction(Long transactionId) {
        Transaction transaction = transactionRepository.findById(transactionId).orElseThrow();
        return transactionConverter.convertToDto(transaction);
    }

    @Transactional
    @Override
    public List<TransactionResponseDto> findTransaction(LocalDate date) {
        List<Transaction> transactions = transactionRepository.findByCreatedDate(date);
        return transactions.stream()
                .map(transactionConverter::convertToDto)
                .collect(Collectors.toList());
    }

    public List<TransactionResponseDto> findAccountTransactionsByPeriod(LocalDate dateFrom, LocalDate dateTo,
                                                                        Long accountId) {
        List<Transaction> transactions = transactionRepository.findTransactionsByPeriod(dateFrom, dateTo);
        return transactions.stream()
                .map(transactionConverter::convertToDto)
                .collect(Collectors.toList());
    }
}
