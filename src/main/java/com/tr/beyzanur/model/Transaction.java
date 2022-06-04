package com.tr.beyzanur.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.tr.beyzanur.model.enums.TransactionType;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Setter
@Getter
@Entity
public class Transaction extends BaseModel{

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate createdDate;

    private UUID sourceIbanNumber;

    private UUID destinationIbanNumber;

    private BigDecimal quantity;

    private TransactionType transactionType;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id")
    private Account account;
}
