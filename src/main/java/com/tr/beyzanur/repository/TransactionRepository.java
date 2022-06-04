package com.tr.beyzanur.repository;

import com.tr.beyzanur.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    @Query(value = "SELECT e.* FROM TRANSACTION e WHERE DATE(creation_date) =:date", nativeQuery = true)
    List<Transaction> findByCreatedDate(LocalDate date);

    @Query("SELECT t FROM Transaction t WHERE t.createdDate >= :dateFrom AND t.createdDate <= :dateTo")
    List<Transaction> findTransactionsByPeriod(@Param("dateFrom") LocalDate dateFrom,
                                               @Param("dateTo") LocalDate dateTo);

    @Query("SELECT CASE WHEN COUNT(u) > 0 THEN true ELSE false END FROM Transaction u WHERE u.destinationIbanNumber = :number")
    boolean existsByIbanNumber(@Param("number") String ibanNumber);

    List<Transaction> findTransactionByAccount_Id(Long accountId);
}
