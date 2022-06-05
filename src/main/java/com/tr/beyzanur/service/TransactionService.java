package com.tr.beyzanur.service;

import com.tr.beyzanur.dto.request.CreateTransactionDto;
import com.tr.beyzanur.dto.response.TransactionResponseDto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public interface TransactionService {

    void createTransaction(CreateTransactionDto createTransactionDto);

    void payDebtFromCardAccounts(Long id, BigDecimal debt);

    void payDebtFromExternalAccount(Long cardId, BigDecimal money, UUID externalAccount);

    List<TransactionResponseDto> findAccountTransactions(Long accountId);

    TransactionResponseDto findTransaction(Long transactionId);

    List<TransactionResponseDto> findTransactionByDate(LocalDate date);

    List<TransactionResponseDto> findTransactionsByPeriod(LocalDate dateFrom, LocalDate dateTo);
}
