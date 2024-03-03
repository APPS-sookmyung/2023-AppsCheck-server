package com.example.appscheck.Member.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class loginDto {
    private String memberEmail;
    private String memberPw;
}
