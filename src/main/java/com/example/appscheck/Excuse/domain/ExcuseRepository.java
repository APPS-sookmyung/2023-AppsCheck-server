package com.example.appscheck.Excuse.domain;

import com.example.appscheck.Event.domain.entity.Event;
import com.example.appscheck.Member.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExcuseRepository extends JpaRepository<Excuse, Integer> {
    Excuse findByMember_MemberId(int memberId);
    //Excuse findByMember_MemberIdAndEvent_EventId(int memberId, int EventId);
    Excuse findByMember_MemberIdAndEvent(int memberId, Event event);
}
