package com.example.appscheck.Excuse.service;

import com.example.appscheck.Checking.domain.Checking;
import com.example.appscheck.Checking.domain.CheckingRepository;
import com.example.appscheck.Event.domain.entity.Event;
import com.example.appscheck.Event.domain.repository.EventRepository;
import com.example.appscheck.Excuse.domain.Excuse;
import com.example.appscheck.Excuse.domain.ExcuseRepository;
import com.example.appscheck.Excuse.dto.ExcuseRequestDto;
import com.example.appscheck.Member.domain.Member;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ExcuseService {
    private final ExcuseRepository excuseRepository;
    private final CheckingRepository checkingRepository;

    @Transactional
    public void excuseSave(ExcuseRequestDto excuseRequestDto, Member member, Event event) {
        Excuse excuse = Excuse.builder()
                .member(member)
                .event(event)
                .excuseDetail(excuseRequestDto.getExcuseDetail())
                .build();
        excuseRepository.save(excuse);

        // checking 테이블에 새로운 레코드 생성
        Checking checking = Checking.builder()
                .member(member)
                .event(event)
                .checkStatus("결석") // 자동으로 결석 상태 설정
                .build();
        checkingRepository.save(checking);
    }
    @Transactional
    public Excuse findByMemberId(int memberId) {
        return excuseRepository.findByMember_MemberId(memberId);
    }

    //checking entity 에서 excuseDetail 가지고 올 수 있도록
    @Transactional
    public Excuse findByMemberIdAndEventId(int memberId, Event event) {
        return excuseRepository.findByMember_MemberIdAndEvent(memberId, event);
    }
}

