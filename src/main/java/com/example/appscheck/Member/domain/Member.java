package com.example.appscheck.Member.domain;

import com.example.appscheck.Event.domain.entity.Event;
import jakarta.persistence.*;
import lombok.*;

@Entity(name="member")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private int memberId;

    @Column
    private String memberName;

    @Column
    private int managerCheck;

    @Column
    private String memberMajor;

    @Column
    private String studentNum;

    @Column
    private String memberNum;

    @Column
    private String memberEmail;

    @Column
    private String memberPw;

    @ManyToOne
    @JoinColumn(name = "event_id")
    private Event event;

}
