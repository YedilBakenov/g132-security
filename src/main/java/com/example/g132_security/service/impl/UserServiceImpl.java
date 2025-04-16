package com.example.g132_security.service.impl;


import com.example.g132_security.model.Permission;
import com.example.g132_security.model.User;
import com.example.g132_security.repository.PermissionRepository;
import com.example.g132_security.repository.UserRepository;
import com.example.g132_security.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PermissionRepository permissionRepository;
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Override
    public User getLogin(String email) {
        return userRepository.getUserByEmail(email);
    }

    @Override
    public Permission getPermission() {
        return permissionRepository.getStandartPermission();
    }

    @Override
    public void addNewUser(User user, String rePassword) {
        User userFromBase = userRepository.getUserByEmail(user.getEmail());

        if(userFromBase!=null) return;

        if(!user.getPassword().equals(rePassword)) return;

        user.setPermissions(List.of(getPermission()));
        user.setPassword(passwordEncoder.encode(rePassword));

        userRepository.save(user);

    }

    public User getCurrentUser(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return (User) authentication.getPrincipal();
    }

    @Override
    public void changePassword(String oldPassword, String newPassword, String reNewPassword) {

        if(!passwordEncoder.matches(oldPassword,getCurrentUser().getPassword())) return;

        if(!newPassword.equals(reNewPassword)) return;

        getCurrentUser().setPassword(passwordEncoder.encode(newPassword));

        userRepository.save(getCurrentUser());

    }
}
