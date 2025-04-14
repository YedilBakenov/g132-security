package com.example.g132_security.service;

import com.example.g132_security.model.User;
import org.springframework.stereotype.Service;

@Service
public interface UserService {

    User getLogin(String email);
}
