package com.example.appscheck.Event.dto;

import com.example.appscheck.Event.domain.entity.Event;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PostResponseDto {
    private int eventId;
    private String eventTime;
    private String eventPlace;
    private String eventDetail;


}
