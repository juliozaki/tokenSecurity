package com.Security.SessionSecurity.Security;

import com.Security.SessionSecurity.Util.JwtUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class CustomAuthorizationFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        String bearerToken = request.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer ")){
            JwtUtil jwtUtil = new JwtUtil();
            String token = bearerToken.replace("Bearer ", "");
            UsernamePasswordAuthenticationToken usernamePat = jwtUtil.getAuthentication(token);
            SecurityContextHolder.getContext().setAuthentication(usernamePat);

        }
        filterChain.doFilter(request, response);
    }
}
