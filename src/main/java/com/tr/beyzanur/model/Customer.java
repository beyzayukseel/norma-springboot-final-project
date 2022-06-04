package com.tr.beyzanur.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@Setter
@Getter
@Entity
public class Customer extends BaseModel{

    private String customerNumber;
    private Boolean isDeleted;
    private String email;

    @OneToOne(cascade = CascadeType.PERSIST)
    private CustomerAddress customerAddress;

    @OneToMany(mappedBy = "customer",fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<Account> accounts;

    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL)
    private Set<Card> cards;

    @OneToOne(cascade = CascadeType.ALL)
    private User user;
}
