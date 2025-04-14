package com.example.g132_security.service.impl;


import com.example.g132_security.model.User;
import com.example.g132_security.repository.UserRepository;
import com.example.g132_security.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public User getLogin(String email) {
        return userRepository.getUserByEmail(email);
    }
}
