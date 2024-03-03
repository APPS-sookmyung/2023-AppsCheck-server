package com.example.appscheck.Member.domain.repository;

import com.example.appscheck.Member.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository  extends JpaRepository<Member, Integer> {
    public Member findByMemberEmail(String memberEmail);
    public boolean existsByMemberEmail(String email);
    public void deleteByMemberEmail(String email);

    Member findByMemberId(int memberId);
}
