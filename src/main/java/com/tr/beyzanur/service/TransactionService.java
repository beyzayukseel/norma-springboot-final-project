package com.tr.beyzanur.service;

import com.tr.beyzanur.dto.response.TransactionResponseDto;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;

public interface TransactionService {
    @Transactional
    List<TransactionResponseDto> findAccountTransactions(Long accountId);

    @Transactional
    TransactionResponseDto findTransaction(Long transactionId);

    @Transactional
    List<TransactionResponseDto> findTransaction(LocalDate date);
}
