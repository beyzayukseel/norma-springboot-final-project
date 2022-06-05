package com.tr.beyzanur.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.tr.beyzanur.model.enums.AccountStatus;
import com.tr.beyzanur.model.enums.AccountType;
import com.tr.beyzanur.model.enums.Currency;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Entity
public class Account extends BaseModel implements Serializable {

    private String accountNumber;
    private String bankCode;
    private BigDecimal balance= BigDecimal.ZERO;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate createdDate;

    private UUID iban;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate modifiedDate;

    @Enumerated(EnumType.STRING)
    private AccountType accountType;

    @Enumerated(EnumType.STRING)
    private Currency currency;

    private AccountStatus accountStatus;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Customer customer;

    @OneToMany(mappedBy = "account", cascade = CascadeType.ALL)
    private Set<Transaction> transactions;

    @ManyToMany(fetch = FetchType.LAZY,cascade = CascadeType.MERGE)
    @JoinTable(
            name = "accounts-cards",
            joinColumns = @JoinColumn(name = "account_id"),
            inverseJoinColumns = @JoinColumn(name = "card_id"))
    private List<Card> cards = new ArrayList<>();

    private Boolean isDeleted;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Account account = (Account) o;
        return Objects.equals(accountNumber, account.accountNumber) && Objects.equals(bankCode, account.bankCode)
                && Objects.equals(balance, account.balance) && Objects.equals(createdDate, account.createdDate)
                && Objects.equals(modifiedDate, account.modifiedDate) && accountType == account.accountType
                && currency == account.currency && accountStatus == account.accountStatus &&
                Objects.equals(customer, account.customer) && Objects.equals(transactions, account.transactions) &&
                Objects.equals(cards, account.cards) && Objects.equals(isDeleted, account.isDeleted);
    }

    @Override
    public int hashCode() {
        return Objects.hash(accountNumber, bankCode, balance, createdDate, modifiedDate, accountType, currency,
                accountStatus, customer, transactions, cards, isDeleted);
    }
}
