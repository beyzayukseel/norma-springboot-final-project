package com.tr.beyzanur.repository;

import com.tr.beyzanur.model.Account;
import com.tr.beyzanur.model.enums.AccountStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;


@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {

    List<Account> getAccountsByCustomerId(Long customerId);

    @Query("SELECT m  from Account m where m.accountStatus  = ?1 ")
    List<Account> getAccountsByAccountStatus(AccountStatus accountStatus);

    boolean existsByIban(UUID iban);

    boolean existsById(Long accountId);

    Boolean existsByCustomerId(Long customerId);

    Account findByIban(UUID iban);
}
