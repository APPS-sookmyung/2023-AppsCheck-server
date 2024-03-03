package com.example.appscheck.Event.domain.repository;

import com.example.appscheck.Event.domain.entity.Event;
import com.example.appscheck.Member.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EventRepository extends JpaRepository<Event, Integer> {

}
