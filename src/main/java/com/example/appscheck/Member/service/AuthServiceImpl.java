package com.example.appscheck.Member.service;

import com.example.appscheck.Auth.JwtProvider;
import com.example.appscheck.Auth.TokenDto;
import com.example.appscheck.Member.domain.Member;
import com.example.appscheck.Member.domain.repository.MemberRepository;
import com.example.appscheck.Member.dto.DeleteMemberRequestDto;
import com.example.appscheck.Member.dto.loginDto;
import com.example.appscheck.Member.dto.signDto;
import com.example.appscheck.exception.DuplicateEmail;
import com.example.appscheck.exception.LogInFailureEmail;
import com.example.appscheck.exception.LogInFailurePassword;
import com.example.appscheck.exception.LogInRequiredException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;




@Service
public class AuthServiceImpl implements AuthService{

    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private JwtProvider jwtProvider;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private AuthenticationManagerBuilder authenticationManagerBuilder;

    public Member getCurrentMember() {

        Member member = memberRepository.findByMemberEmail(SecurityContextHolder.getContext().getAuthentication().getName());
        if(member==null) {
            throw new LogInRequiredException();
        }
        return member;
    }

    @Transactional
    public void deleteMember(DeleteMemberRequestDto dto) {
        Member member = getCurrentMember();
        validatePassword(dto.getMemberPw(),member);
        String email = member.getMemberEmail();
        memberRepository.deleteByMemberEmail(email);
    }

    public void validatePassword(String getPassword, Member member) {
        if (!(passwordEncoder.matches(getPassword, member.getMemberPw()))) {
            throw new LogInFailurePassword();
        }
    }



    @Transactional
    @Override
    public void signUpUser(signDto signDto) {
        Member member = Member.builder()
                .studentNum(signDto.getStudentNum())
                .memberName(signDto.getMemberName())
                .managerCheck(signDto.getManagerCheck())
                .memberMajor(signDto.getMemberMajor())
                .memberNum(signDto.getMemberNum())
                .memberEmail(signDto.getMemberEmail())
                .memberPw(passwordEncoder.encode(signDto.getMemberPw()))
                .build();
        memberRepository.save(member);
    }

    @Transactional
    public TokenDto loginUser(loginDto dto) {
        String email = dto.getMemberEmail();
        if(!(memberRepository.existsByMemberEmail(email))){
            throw new LogInFailureEmail();
        }
        Member member = memberRepository.findByMemberEmail(dto.getMemberEmail());
        checkPassword(dto.getMemberPw(), member.getMemberPw());


        // user 검증
        Authentication authentication = setAuthentication(dto);
        // token 생성
        String accessToken = jwtProvider.generateAccessToken(authentication);
        User user = (User) authentication.getPrincipal(); // user 정보

        // TokenDto에 memberId 설정
        int memberId = member.getMemberId();

        return TokenDto.builder()
                .accessToken(accessToken)
                .memberId(memberId)
                .build();
    }
    public void checkPassword(String getPassword, String password) {
        if (!(passwordEncoder.matches(getPassword, password))) {
            throw new LogInFailurePassword();
        }
    }

    public Authentication setAuthentication(loginDto dto) {
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(dto.getMemberEmail(), dto.getMemberPw());
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return authentication;
    }

    // memberId로 멤버를 찾는 메서드 추가
    public Member findById(int memberId) {
        return memberRepository.findByMemberId(memberId);
    }


}