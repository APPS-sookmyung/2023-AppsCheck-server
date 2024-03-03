package com.example.appscheck.Auth;
import com.example.appscheck.Member.domain.repository.MemberRepository;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.security.Key;
import java.util.Date;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class JwtProvider {

    private Key secretKey;
    @Value("${jwt.secret-key}") String key;
    private static final long EXPIRE_TIME=1000*60*2*30*60; //1분
    private static final long REFRESH_EXPIRE_TIME = 10*60*60*24*7; //7일
    private static final String SUCCESS = "success";
    private static final String EXPIRED = "expired";
    private static final String DENIED = "denied";
    private long now;
    private final String AUTHORITIES_KEY = "auth";
    private final CustomUserDetailsService customUserDetailsService;
    private final MemberRepository memberRepository;


    @PostConstruct
    protected void init() {
        secretKey = Keys.hmacShaKeyFor(Decoders.BASE64.decode(key));
        //secretKey = Keys.hmacShaKeyFor(key.getBytes(StandardCharsets.UTF_8));
        now= new Date().getTime();
    }

    public String generateAccessToken(Authentication authentication) {
        return generateToken(authentication, EXPIRE_TIME);
    }


    public String generateToken(Authentication authentication, long expireTime) {
        now= new Date().getTime();
        Date expiration = new Date(now+expireTime);

        String authorities = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));

        return Jwts.builder()
                .setSubject(authentication.getName())
                .claim(AUTHORITIES_KEY, authorities)
                .setIssuedAt(new Date(now))
                .setExpiration(expiration)
                .signWith(secretKey, SignatureAlgorithm.HS256)
                .compact();
    }


    //token으로부터 authentication을 얻는 것
    public Authentication getAuthentication(String token) {
        //name으로부터 userDetails 얻음
        UserDetails userDetails = customUserDetailsService.loadUserByUsername(this.getAccount(token));
        //Authentication의 구현체(Username..) 얻음
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());

    }

    //token으로부터 name 알아냄
    public String getAccount(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(secretKey).build()
                .parseClaimsJws(token).getBody().getSubject();
    }

    //request에 헤더설정 해줘야 함
    public String resolveToken(HttpServletRequest request, String header) {
        String bearerToken = request.getHeader(header);
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }

    public String validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(secretKey).build().parseClaimsJws(token);
            return SUCCESS;
        } catch (ExpiredJwtException e) { // 기한 만료
            System.out.println("in p");
            return EXPIRED; //AT만료는 필터에서 걸러짐, 여기서 걸러지는건 RT의 만료
        } catch (IllegalArgumentException e) {
            return DENIED;
        }

    }
}