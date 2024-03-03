package com.example.appscheck.Auth;
import com.example.appscheck.Member.domain.Member;
import com.example.appscheck.Member.domain.repository.MemberRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@Service
@AllArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return createUserDetails(memberRepository.findByMemberEmail(email));
    }

    private UserDetails createUserDetails(Member member) {
        return User.builder()
                .username(member.getMemberEmail())
                .password(member.getMemberPw())
                .authorities(new SimpleGrantedAuthority(toString()).toString())
                .build();
    }

}