package com.example.appscheck.Member.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MainPageResponseDto {
    private int managerCheck;
    private int eventId;
    private String eventTime;
    private String eventPlace;
    private String eventDetail;
}
