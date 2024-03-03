package com.example.appscheck.Excuse.service;

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

    @Transactional
    public void excuseSave(ExcuseRequestDto excuseRequestDto,Member member) {
        Excuse excuse = Excuse.builder()
                .member(member)
                .excuseDetail(excuseRequestDto.getExcuseDetail())
                .build();
        excuseRepository.save(excuse);
    }
    @Transactional
    public Excuse findByMemberId(int memberId) {
        return excuseRepository.findByMember_MemberId(memberId);
    }

}
