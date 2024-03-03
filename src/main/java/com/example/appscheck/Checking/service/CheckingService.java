package com.example.appscheck.Checking.service;

import com.example.appscheck.Checking.domain.Checking;
import com.example.appscheck.Checking.domain.CheckingRepository;
import com.example.appscheck.Checking.dto.CheckRequestDto;
import com.example.appscheck.Event.domain.entity.Event;
import com.example.appscheck.Member.domain.Member;
import com.example.appscheck.Member.service.AuthServiceImpl;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CheckingService {
    private final CheckingRepository checkingRepository;

    //출석체크 정보 등록
    @Transactional
    public void checkAttend(CheckRequestDto checkRequestDto, Member member, Event event) {
        LocalDateTime stampTime = LocalDateTime.parse(checkRequestDto.getStampTime());
        LocalDateTime eventDateTime = LocalDateTime.parse(event.getEventTime());

        //id 값으로 받았지만, db 에는 member 이름도 저장하기
        String addName = member.getMemberName();

        // 출석, 지각, 결석 판단
        String checkStatus;
        Duration duration = Duration.between(eventDateTime, stampTime);
        if (duration.toMinutes() < 1) {
            checkStatus = "출석";
        } else if (duration.toMinutes() < 30) {
            checkStatus = "지각";
        } else {
            checkStatus = "결석";
        }

        // Checking 엔티티 생성 및 저장
        Checking checking = Checking.builder()
                .member(member)
                .event(event)
                .stampTime(checkRequestDto.getStampTime())
                .checkStatus(checkStatus)
                .memberName(addName)
                .build();

        checkingRepository.save(checking);
    }

    //전체 출결 데이터 조회
    public List<Checking> getAllCheckings() {
        return checkingRepository.findAll();
    }
    //개인 회원이 출결 데이터 조회
    public List<Checking> getCheckingsByMemberId(int memberId) {
        return checkingRepository.findByMember_MemberId(memberId);
    }

}
