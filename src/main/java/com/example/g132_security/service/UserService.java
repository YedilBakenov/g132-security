package com.example.g132_security.service;

import com.example.g132_security.model.Permission;
import com.example.g132_security.model.User;
import org.springframework.stereotype.Service;

@Service
public interface UserService {

    User getLogin(String email);

    Permission getPermission();

    void addNewUser(User user, String rePassword);

    void changePassword(String oldPassword, String newPassword, String reNewPassword);
}
