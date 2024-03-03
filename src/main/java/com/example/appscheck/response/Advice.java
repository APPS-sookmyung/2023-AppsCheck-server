package com.example.appscheck.response;

import com.example.appscheck.exception.DuplicateEmail;
import com.example.appscheck.exception.LogInFailureEmail;
import com.example.appscheck.exception.LogInFailurePassword;

import com.example.appscheck.exception.LogInRequiredException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;



@RestControllerAdvice
public class Advice {
    @ExceptionHandler(DuplicateEmail.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public Response DuplicateEmailResponse() {
        return Response.failure(HttpStatus.CONFLICT, "중복 이메일입니다.");
    }

    @ExceptionHandler(LogInFailureEmail.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public Response loginFailureEmailResponse() {
        return Response.failure(HttpStatus.UNAUTHORIZED, "존재하지 않는 email 입니다.");
    }
    @ExceptionHandler(LogInFailurePassword.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public Response loginFailurePasswordResponse() {
        return Response.failure(HttpStatus.UNAUTHORIZED, "비밀번호가 일치하지 않습니다.");
    }


    @ExceptionHandler(LogInRequiredException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public Response NotFoundExceptionResponse() {
        return Response.failure(HttpStatus.UNAUTHORIZED, "회원/기타 정보 없음");
    }

}