package com.Security.SessionSecurity.Security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.security.core.Authentication;

import java.io.IOException;

@Component
public class CustomAuthenticationFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;
    public CustomAuthenticationFilter(JwtUtil jwtUtil){
        this.jwtUtil = jwtUtil;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest httpRequest,
                                    HttpServletResponse httpResponse,
                                    FilterChain filterChain) throws ServletException, IOException {

        Authentication auth = SecurityContextHolder
                .getContext()
                .getAuthentication();
        if(auth != null){
            UserDetails userDetail = (UserDetails) auth.getPrincipal();
            if(userDetail != null){
                String token = this.jwtUtil.encode(userDetail.getUsername());
                httpResponse.addHeader("Authorization", token);
                httpResponse.getWriter().flush();
            }

        }
        System.out.println("JULIOZAKI");
        filterChain.doFilter(httpRequest, httpResponse);
    }
}
