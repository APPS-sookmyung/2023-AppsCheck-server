package com.example.appscheck.Event.controller;

import com.example.appscheck.Auth.TokenDto;
import com.example.appscheck.Event.dto.PostDeleteDto;
import com.example.appscheck.Event.dto.PostRequestDto;
import com.example.appscheck.Event.dto.PostResponseDto;
import com.example.appscheck.Event.service.PostService;
import com.example.appscheck.Member.dto.DeleteMemberRequestDto;
import com.example.appscheck.Member.dto.signDto;
import com.example.appscheck.response.Response;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import static com.example.appscheck.response.Message.*;
import static com.example.appscheck.response.Response.success;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/event")
@RequiredArgsConstructor
public class EventPostController {
    private final PostService postService;

    //이벤트 생성
    @ResponseStatus(OK)
    @PostMapping("/post")
    public Response EventPost(@RequestBody PostRequestDto postRequestDto) {
        PostResponseDto response = postService.postSave(postRequestDto);
        return success(EVENT_SAVE_SUCCESS, response);
    }

    //이벤트 삭제
    @ResponseStatus(OK)
    @DeleteMapping("/delete")
    public Response EventDelete(@RequestBody PostDeleteDto postDeleteDto) {
        postService.postDelete(postDeleteDto);
        return success(DELETE_EVENT_SUCCESS);
    }

}
