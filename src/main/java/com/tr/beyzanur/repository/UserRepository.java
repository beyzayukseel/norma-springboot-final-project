package com.tr.beyzanur.repository;

import com.tr.beyzanur.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);

    Boolean existsByIdentifyNumber(String identifyNumber);

    Boolean existsByEmail(String email);

    User findByIdentifyNumber(String identifyNumber);
}
