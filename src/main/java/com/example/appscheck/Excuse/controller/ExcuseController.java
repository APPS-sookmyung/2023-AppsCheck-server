package com.example.appscheck.Excuse.controller;


import com.example.appscheck.Event.domain.entity.Event;
import com.example.appscheck.Event.dto.PostRequestDto;
import com.example.appscheck.Event.dto.PostResponseDto;
import com.example.appscheck.Event.service.PostService;
import com.example.appscheck.Excuse.dto.ExcuseRequestDto;
import com.example.appscheck.Excuse.service.ExcuseService;
import com.example.appscheck.Member.domain.Member;
import com.example.appscheck.Member.service.AuthServiceImpl;
import com.example.appscheck.response.Response;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import static com.example.appscheck.response.Message.EVENT_SAVE_SUCCESS;
import static com.example.appscheck.response.Message.EXCUSE_SAVE_SUCCESS;
import static com.example.appscheck.response.Response.failure;
import static com.example.appscheck.response.Response.success;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/excuse")
@RequiredArgsConstructor
public class ExcuseController {
    private final ExcuseService excuseService;
    private final AuthServiceImpl authService;
    private final PostService postService;

    //결석사유 작성
    @ResponseStatus(OK)
    @PostMapping("/post")
    public Response EventPost(@RequestBody ExcuseRequestDto excuseRequestDto) {
        Member member = authService.findById(excuseRequestDto.getMemberId());
        Event event = postService.findById(excuseRequestDto.getEventId());
        excuseService.excuseSave(excuseRequestDto, member, event);

        return success(EXCUSE_SAVE_SUCCESS);
    }
}
