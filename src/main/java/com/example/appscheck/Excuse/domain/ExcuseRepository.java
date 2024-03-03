package com.example.appscheck.Excuse.domain;

import com.example.appscheck.Event.domain.entity.Event;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExcuseRepository extends JpaRepository<Excuse, Integer> {
    Excuse findByMember_MemberId(int memberId);
}
