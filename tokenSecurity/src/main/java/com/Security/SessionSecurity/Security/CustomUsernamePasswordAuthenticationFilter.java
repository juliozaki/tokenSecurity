package com.Security.SessionSecurity.Security;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;
import java.util.Collections;

public class CustomUsernamePasswordAuthenticationFilter extends UsernamePasswordAuthenticationFilter{

    private JwtUtil jwtUtil;

    public CustomUsernamePasswordAuthenticationFilter(JwtUtil jwtUtil){
        this.jwtUtil = jwtUtil;
    }

    protected void successfulAuthentication(HttpServletRequest httpRequest,
                                            HttpServletResponse httpResponse,
                                            FilterChain filterChain,
                                            Authentication authentication) throws IOException, ServletException {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        System.out.println("JULIOZAKI el nuevo" + userDetails.getUsername());
        String token = this.jwtUtil.encode(userDetails.getUsername());
        httpResponse.addHeader("Authorization", token);
        httpResponse.getWriter().flush();
    }
}
