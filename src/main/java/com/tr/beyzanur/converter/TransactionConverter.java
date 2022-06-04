package com.tr.beyzanur.converter;


import com.tr.beyzanur.dto.request.CreateTransactionDto;
import com.tr.beyzanur.dto.response.TransactionResponseDto;
import com.tr.beyzanur.model.Transaction;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

@Component
public class TransactionConverter {

    public Transaction convertToEntity(CreateTransactionDto dto) {
        Transaction transaction = new Transaction();
        if (dto != null){
            BeanUtils.copyProperties(dto,transaction);
        }
        return transaction;
    }

    public TransactionResponseDto convertToDto(Transaction entity) {
        TransactionResponseDto transactionResponseDto = new TransactionResponseDto();
        if (entity != null){
            BeanUtils.copyProperties(entity,transactionResponseDto);
        }
        return transactionResponseDto;
    }


}
