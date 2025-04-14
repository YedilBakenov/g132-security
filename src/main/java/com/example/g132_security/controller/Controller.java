package com.example.g132_security.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;

@org.springframework.stereotype.Controller
@RequiredArgsConstructor
public class Controller {

    @GetMapping(value = "/sign-in")
    public String signIn(){
        return "sign-in";
    }

    @GetMapping(value = "/")
    public String index(){
        return "index";
    }
}
