package com.example.appscheck.Event.dto;

import com.example.appscheck.Event.domain.entity.Event;
import lombok.AllArgsConstructor;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PostRequestDto {
    private String eventTime;
    private String eventPlace;
    private String eventDetail;

}
