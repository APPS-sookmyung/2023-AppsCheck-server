package com.example.appscheck.Member.service;


import com.example.appscheck.Auth.TokenDto;
import com.example.appscheck.Member.dto.loginDto;
import com.example.appscheck.Member.dto.signDto;

public interface AuthService {
    void signUpUser(signDto signDto);

    TokenDto loginUser(loginDto loginDto);

}