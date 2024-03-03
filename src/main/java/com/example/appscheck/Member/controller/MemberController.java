package com.example.appscheck.Member.controller;

import com.example.appscheck.Auth.TokenDto;
import com.example.appscheck.Event.domain.entity.Event;
import com.example.appscheck.Event.service.PostService;
import com.example.appscheck.Member.domain.Member;
import com.example.appscheck.Member.dto.*;
import com.example.appscheck.Member.service.AuthService;
import com.example.appscheck.Member.service.AuthServiceImpl;
import com.example.appscheck.response.Response;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.example.appscheck.response.Message.*;
import static com.example.appscheck.response.Response.success;
import static org.springframework.http.HttpStatus.OK;


@RestController
@RequiredArgsConstructor
public class MemberController {
    private final AuthService authService;
    private final AuthServiceImpl authServiceImpl;
    private final PostService postService;


    @ResponseStatus(OK)
    @PostMapping("/sign-up")
    public Response signUp(@RequestBody signDto signDto) {
        authService.signUpUser(signDto);
        return success(SIGN_UP_SUCCESS);
    }
    @ResponseStatus(OK)
    @PostMapping("/log-in")
    public Response logIn(@RequestBody loginDto loginDto) {
        TokenDto response = authService.loginUser(loginDto);
        return success(LOG_IN_SUCCESS,response);
    }

    @ResponseStatus(OK)
    @DeleteMapping("/user/delete")
    public Response deleteMember(@RequestBody DeleteMemberRequestDto deleteMemberRequestDto) {
        authServiceImpl.deleteMember(deleteMemberRequestDto);
        return success(DELETE_MEMBER_SUCCESS);
    }

    @GetMapping("/show/event")
    public Response showAllEvent(@RequestParam int memberId) {
        Member member = authServiceImpl.findById(memberId);
        int managerCheck = member.getManagerCheck();
        List<Event> events = postService.getAllEvents();

        // 응답 DTO를 생성
        List<MainPageResponseDto> responseDtoList = new ArrayList<>();
        for (Event event : events) {
            // 이벤트 정보와 멤버의 managerCheck 값을 설정하여 응답 DTO를 생성합니다.
            MainPageResponseDto responseDto = new MainPageResponseDto();
            responseDto.setManagerCheck(managerCheck);
            responseDto.setEventId(event.getEventId());
            responseDto.setEventTime(event.getEventTime());
            responseDto.setEventPlace(event.getEventPlace());
            responseDto.setEventDetail(event.getEventDetail());

            responseDtoList.add(responseDto);
        }
        return success(SHOW_ALL_SUCCESS, responseDtoList);
    }

}