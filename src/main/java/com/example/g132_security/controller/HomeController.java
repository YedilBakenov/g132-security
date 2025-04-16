package com.example.g132_security.controller;

import com.example.g132_security.model.User;
import com.example.g132_security.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
public class HomeController {

    private final UserService userService;

    @PreAuthorize("isAnonymous()")
    @GetMapping(value = "/sign-in")
    public String signIn(){
        return "sign-in";
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping(value = "/")
    public String index(){
        return "index";
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @GetMapping(value = "/admin")
    public String adminPage(){
        return "admin-page";
    }

    @GetMapping(value = "/forbidden")
    public String accessDeniedPage(){
        return "access-page";
    }

    @PreAuthorize("isAnonymous()")
    @GetMapping(value = "/registration")
    public String registrationPage(){
        return "register-page";
    }

    @PreAuthorize("isAnonymous()")
    @PostMapping(value = "/registration")
    public String addUser(User user, @RequestParam String rePassword){
        userService.addNewUser(user, rePassword);
        return "redirect:/";
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping(value = "/change-password")
    public String changePas(@RequestParam String oldPassword,
                            @RequestParam String newPassword,
                            @RequestParam String reNewPassword){

        userService.changePassword(oldPassword, newPassword, reNewPassword);

        return "sign-in";
    }
}
