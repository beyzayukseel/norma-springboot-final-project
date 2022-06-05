package com.tr.beyzanur.controller;

import com.tr.beyzanur.dto.request.CreateCardRequest;
import com.tr.beyzanur.dto.request.CreateTransactionDto;

import com.tr.beyzanur.dto.response.TransactionResponseDto;
import com.tr.beyzanur.service.TransactionService;
import com.tr.beyzanur.util.constant.MessageResponse;
import com.tr.beyzanur.validator.implementation.EntityIdValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;


@RequiredArgsConstructor
@RestController
@RequestMapping( "/api/v1/transactions")
public class TransactionController {

    private final TransactionService transactionService;
    private final EntityIdValidator entityIdValidator;

    @PostMapping()
    public ResponseEntity<?> createTransaction(@RequestBody CreateTransactionDto createTransactionDto) {
        transactionService.createTransaction(createTransactionDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(new MessageResponse( "Transaction happened successfully!"));
    }

    @PutMapping("/{id}/{debt}")
    public ResponseEntity<MessageResponse> payDebtCardAccounts(@PathVariable Long id, @PathVariable BigDecimal debt) {
        entityIdValidator.validate(id, "transaction");
        transactionService.payDebtFromCardAccounts(id,debt);
        return ResponseEntity.status(HttpStatus.OK).body(new MessageResponse( "Paid Debt Cart successfully!"));
    }

    @PutMapping("/{id}/{money}/{iban}")
    public ResponseEntity<MessageResponse> payDebtExternalAccounts(@PathVariable Long id, @PathVariable BigDecimal money,
                                                                   @PathVariable UUID iban) {
        entityIdValidator.validate(id, "transaction");
        transactionService.payDebtFromExternalAccount(id,money,iban);
        return ResponseEntity.status(HttpStatus.OK).body(new MessageResponse( "Paid Debt Cart successfully!"));
    }

    @GetMapping( "/account/{accountId}")
    public ResponseEntity<List<TransactionResponseDto>> findAccountTransactions(@PathVariable Long accountId) {
        entityIdValidator.validate(accountId, "account");
        return ResponseEntity.ok(transactionService.findAccountTransactions(accountId));
    }

    @GetMapping( "/{transactionId}")
    public ResponseEntity<TransactionResponseDto> findTransaction(@PathVariable Long transactionId) {
        entityIdValidator.validate(transactionId, "transaction");
        return ResponseEntity.ok(transactionService.findTransaction(transactionId));
    }

    @GetMapping( "/date/{date}")
    public ResponseEntity<List<TransactionResponseDto>> findTransactionByDate(@PathVariable LocalDate date) {
        return ResponseEntity.ok(transactionService.findTransactionByDate(date));
    }

    @GetMapping( "/{dateFrom}/{dateTo}")
    public ResponseEntity<List<TransactionResponseDto>> findTransactionByPeriod(@PathVariable LocalDate dateFrom,
                                                                                @PathVariable LocalDate dateTo) {
        return ResponseEntity.ok(transactionService.findTransactionsByPeriod(dateFrom,dateTo));
    }
}
