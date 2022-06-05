package com.tr.beyzanur.service;

import com.tr.beyzanur.model.User;
import org.springframework.data.domain.Page;

public interface UserService {
    void deleteUser(Long userId);

    void saveUser(User user);

    User findById(Long userId);

    User findByEmail(String email);

    Page<User> getAllUsers(int pageSize, int pageNumber);

    User findByIdentifyNumber(String identifyNumber);


    User findByUserName(String userName);
}
