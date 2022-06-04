package com.tr.beyzanur.model;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.OneToOne;

@NoArgsConstructor
@AllArgsConstructor
@Entity
@Getter
@Setter
public class CustomerAddress extends BaseModel {

    private String phoneNumber;
    private String country;
    private String city;
    private String postalCode;
    private String description;

    @OneToOne
    private Customer customer;
}
