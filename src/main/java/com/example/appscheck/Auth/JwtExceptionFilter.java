package com.example.appscheck.Auth;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.filter.OncePerRequestFilter;
import java.io.IOException;

public class JwtExceptionFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        System.out.println("doing jwtExceptionFilter");

        try {
            filterChain.doFilter(request, response); // go to 'JwtAuthenticationFilter'
        } catch (JwtException ex) {
            ex.printStackTrace();
        }

    }

    //AT 오류처리 : 만료, 거부
    public void setErrorResponse(HttpStatus status, HttpServletResponse res, Throwable ex) throws IOException {
        res.setStatus(status.value());
        res.setContentType("application/json; charset=UTF-8");

        ObjectNode json = new ObjectMapper().createObjectNode();
        json.put("message", ex.getMessage());
        json.put("code",HttpStatus.UNAUTHORIZED.value());

        String newResponse = new ObjectMapper().writeValueAsString(json);
        res.getWriter().write(newResponse);
    }
}