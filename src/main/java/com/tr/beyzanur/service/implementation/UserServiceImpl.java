package com.tr.beyzanur.service.implementation;

import com.tr.beyzanur.model.User;
import com.tr.beyzanur.repository.UserRepository;
import com.tr.beyzanur.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public User findByIdentifyNumber(String identifyNumber) {
        return userRepository.findByIdentifyNumber(identifyNumber);
    }

    @Override
    public void deleteUser(Long userId) {
        userRepository.deleteById(userId);
    }

    @Override
    public void saveUser(User user) {
        userRepository.save(user);
    }

    @Override
    public User findByUserName(String userName) {
        return userRepository.findByUsername(userName);
    }

    @Override
    public Page<User> getAllUsers(int pageSize, int pageNumber) {
        Pageable paged= PageRequest.of(pageNumber,pageSize);
        return (Page<User>) userRepository.findAll(paged);
    }
}
