package com.example.appscheck.Excuse.domain;

import com.example.appscheck.Member.domain.Member;
import jakarta.persistence.*;
import lombok.*;

@Entity(name="excuse")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Excuse {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name ="excuse_id")
    private int ExcuseId;

    @Column
    private String excuseDetail;

    //한명의 멤버가 여러번의 결석을 할 수 있음
    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

}
