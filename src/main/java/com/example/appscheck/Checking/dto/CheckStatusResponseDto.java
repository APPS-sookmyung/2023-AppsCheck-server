package com.example.appscheck.Checking.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CheckStatusResponseDto {
    private String memberName;
    private String stampTime;
    private String checkStatus;
    private String excuseDetail;
}
