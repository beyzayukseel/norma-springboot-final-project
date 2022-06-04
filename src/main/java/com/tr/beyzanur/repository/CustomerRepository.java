package com.tr.beyzanur.repository;

import com.tr.beyzanur.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer,Long> {
}
