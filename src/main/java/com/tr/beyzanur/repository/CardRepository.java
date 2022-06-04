package com.tr.beyzanur.repository;
import com.tr.beyzanur.model.Card;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CardRepository extends JpaRepository<Card, Long> {

    List<Card> getCardsByCustomer_Id(Long userId);

    @Query("SELECT c  from Card c where c.id  = ?1 ")
    List<Card> getCardByAccounts(Long cardId);
}
