package com.tr.beyzanur.model;

import com.tr.beyzanur.model.enums.CardStatus;
import com.tr.beyzanur.model.enums.CardType;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


@Entity
@Setter
@Getter
public class Card extends BaseModel {

    private String cardNumber;
    private BigDecimal boundary=BigDecimal.ZERO;
    private BigDecimal debt = BigDecimal.ZERO;
    private Boolean isDeleted;
    private CardType cardType;
    private LocalDate createDate;
    private LocalDate paymentDate;

    @Enumerated(EnumType.STRING)
    private CardStatus cardStatus;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @ManyToMany(fetch = FetchType.LAZY, cascade = {
            CascadeType.PERSIST, CascadeType.MERGE
    })
    @JoinTable(
            name = "accounts-cards",
            joinColumns = @JoinColumn(name = "card_id"),
            inverseJoinColumns = @JoinColumn(name = "account_id"))
    private List<Account> accountList=new ArrayList<>();


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Card card = (Card) o;
        return Objects.equals(cardNumber, card.cardNumber) && Objects.equals(boundary, card.boundary) && Objects.equals(debt, card.debt) && Objects.equals(isDeleted, card.isDeleted) && cardType == card.cardType && cardStatus == card.cardStatus && Objects.equals(customer, card.customer) && Objects.equals(accountList, card.accountList);
    }

    @Override
    public int hashCode() {
        return Objects.hash(cardNumber, boundary, debt, isDeleted, cardType, cardStatus, customer, accountList);
    }
}
