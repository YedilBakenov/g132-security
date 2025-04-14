package com.example.g132_security.config;

import com.example.g132_security.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@org.springframework.context.annotation.Configuration
@EnableMethodSecurity
@EnableWebSecurity
@RequiredArgsConstructor
public class Configuration {

    private UserService userService;

    @Bean
    public UserDetailsService userService(){
        return username -> {
            var user = userService.getLogin(username);

            if(user==null) throw new UsernameNotFoundException("User Not Found");
            else return user;
        };
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception{
        var auth = httpSecurity.getSharedObject(AuthenticationManagerBuilder.class);
            auth
                    .userDetailsService(userService())
                    .passwordEncoder(passwordEncoder());

        httpSecurity.csrf(AbstractHttpConfigurer::disable);

        httpSecurity.exceptionHandling(e -> e.accessDeniedPage("/forbidden"));

        httpSecurity.formLogin(fl ->
                fl.loginProcessingUrl("/auth")
                        .usernameParameter("email")
                        .passwordParameter("password")
                        .loginPage("/sign-in")
                        .defaultSuccessUrl("/", true)
                        .failureUrl("/sign-in?error"));

        httpSecurity.logout(lg -> lg.logoutUrl("/log-out").logoutSuccessUrl("/sign-in"));

        return httpSecurity.build();


    }



}
