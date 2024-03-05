package com.example.appscheck.Checking.controller;

import com.example.appscheck.Checking.domain.Checking;
import com.example.appscheck.Checking.dto.CheckGetResponseDto;
import com.example.appscheck.Checking.dto.CheckRequestDto;
import com.example.appscheck.Checking.dto.CheckStatusResponseDto;
import com.example.appscheck.Checking.service.CheckingService;
import com.example.appscheck.Event.domain.entity.Event;
import com.example.appscheck.Event.service.PostService;
import com.example.appscheck.Excuse.domain.Excuse;
import com.example.appscheck.Excuse.service.ExcuseService;
import com.example.appscheck.Member.domain.Member;
import com.example.appscheck.Member.service.AuthServiceImpl;
import com.example.appscheck.exception.NoEventDataException;
import com.example.appscheck.response.Response;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import static com.example.appscheck.response.Message.*;
import static com.example.appscheck.response.Response.failure;
import static com.example.appscheck.response.Response.success;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/attend")
@RequiredArgsConstructor
public class CheckingController {
    private final CheckingService checkingService;
    private final ExcuseService excuseService;
    private final AuthServiceImpl authService;
    private final PostService postService;

    //출석 체크
    @ResponseStatus(OK)
    @PostMapping("/post")
    public Response checkingAttend(@RequestBody CheckRequestDto checkRequestDto) {
        Member member = authService.findById(checkRequestDto.getMemberId());
        Event event = postService.findById(checkRequestDto.getEventId());
        checkingService.checkAttend(checkRequestDto, member, event);
        return success(ATTEND_SAVE_SUCCESS);
    }

    //관리자 : 출석 정보 전체 조회
    @ResponseStatus(OK)
    @GetMapping("/manager/check")
    public Response checkAllStatus() {
        List<Checking> checkings = checkingService.getAllCheckings();
        List<CheckStatusResponseDto> checkStatusResponseDtos = new ArrayList<>();

        for (Checking checking : checkings) {
            CheckStatusResponseDto checkStatusResponseDto = new CheckStatusResponseDto();
            checkStatusResponseDto.setMemberName(checking.getMemberName());
            checkStatusResponseDto.setStampTime(checking.getStampTime());
            checkStatusResponseDto.setCheckStatus(checking.getCheckStatus());

            // 해당 memberId에 대한 Excuse 데이터 가져오기
            int memberId = checking.getMember().getMemberId();
            Excuse excuse = excuseService.findByMemberId(memberId);
            if (excuse != null) {
                checkStatusResponseDto.setExcuseDetail(excuse.getExcuseDetail());
            }

            checkStatusResponseDtos.add(checkStatusResponseDto);
        }

        return success(MANAGER_ALLCHECK_SUCCESS, checkStatusResponseDtos);
    }

    //개인회원 : 출석 정보 조회
    @ResponseStatus(OK)
    @GetMapping("/self/check")
    public Response checkSelfStatus(@RequestParam int memberId) {
        // 해당 memberId에 해당하는 멤버의 체크 정보를 가져옴
        List<Checking> selfCheck = checkingService.getCheckingsByMemberId(memberId);
        List<CheckStatusResponseDto> selfResponseDtos = new ArrayList<>();

        for (Checking checking : selfCheck) {
            CheckStatusResponseDto checkStatusResponseDto = new CheckStatusResponseDto();
            checkStatusResponseDto.setMemberName(checking.getMemberName());
            checkStatusResponseDto.setStampTime(checking.getStampTime());
            checkStatusResponseDto.setCheckStatus(checking.getCheckStatus());

            // 해당 체크에 대한 결석 사유를 가져옴
            String excuseDetail = checkingService.getExcuseDetailByMemberIdAndEventId(memberId, checking.getEvent());
            if (excuseDetail != null) {
                checkStatusResponseDto.setExcuseDetail(excuseDetail);
            } else {
                checkStatusResponseDto.setExcuseDetail("No excuse provided"); // 결석 사유가 없는 경우 기본값 설정
            }

            selfResponseDtos.add(checkStatusResponseDto);
        }

        return success(SELF_ALLCHECK_SUCCESS, selfResponseDtos);
    }

}
