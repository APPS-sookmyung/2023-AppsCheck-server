package com.example.appscheck.Checking.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

//출석체크
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CheckRequestDto {
    private int memberId;
    private String stampTime;
    private int eventId;
}
