package com.example.appscheck.Member.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class signDto {

    private String memberName;

    private int managerCheck;

    private String memberMajor;

    private String studentNum;

    private String memberNum;

    private String memberEmail;

    private String memberPw;
}
