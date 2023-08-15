package com.Security.SessionSecurity.Security;

import com.Security.SessionSecurity.Dto.TokenDto;
import com.Security.SessionSecurity.Util.JwtUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.AccessDeniedException;

@Component
public class CustomAuthorizationFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest,
                                    HttpServletResponse httpServletResponse,
                                    FilterChain filterChain) throws ServletException, IOException{
        boolean accepted = true;
        try {
            String bearerToken = httpServletRequest.getHeader("Authorization");
            if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
                JwtUtil jwtUtil = new JwtUtil();
                String token = bearerToken.replace("Bearer ", "");
                UsernamePasswordAuthenticationToken usernamePat = jwtUtil.getAuthentication(token);
                if(usernamePat != null){
                    SecurityContextHolder.getContext().setAuthentication(usernamePat);
                }else {
                    failResponse(httpServletResponse);
                    accepted = false;
                }

            }
        }catch (IllegalArgumentException e){
            failResponse(httpServletResponse);
            accepted = false;
        }
        if (accepted){
            filterChain.doFilter(httpServletRequest, httpServletResponse);
        }

    }

    private void failResponse(HttpServletResponse httpServletResponse) throws IOException{
        TokenDto unsuccessful = new TokenDto(null, HttpStatus.UNAUTHORIZED.value(), "Bad token");
        httpServletResponse.setContentType(MediaType.APPLICATION_JSON_VALUE);
        httpServletResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        OutputStream responseStream = httpServletResponse.getOutputStream();
        ObjectMapper mapper = new ObjectMapper();
        mapper.writeValue(responseStream, unsuccessful);
        responseStream.flush();
    }
}
