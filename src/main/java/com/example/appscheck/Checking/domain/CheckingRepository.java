package com.example.appscheck.Checking.domain;

import com.example.appscheck.Event.domain.entity.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CheckingRepository extends JpaRepository<Checking, Integer> {
    List<Checking> findByMember_MemberId(int memberId);


}
