package com.example.appscheck.Checking.domain;

import com.example.appscheck.Event.domain.entity.Event;
import com.example.appscheck.Excuse.domain.Excuse;
import com.example.appscheck.Member.domain.Member;
import jakarta.persistence.*;
import lombok.*;

import java.sql.Timestamp;

@Entity(name="checking")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Checking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "check_id")
    private int checkId;

    @Column
    private String stampTime;

    @Column
    private String checkStatus;

    @Column
    private String memberName;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne
    @JoinColumn(name = "excuse_id")
    private Excuse excuse;

    @OneToOne
    @JoinColumn(name = "event_id")
    private Event event;


}
