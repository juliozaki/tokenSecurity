package com.Security.SessionSecurity.Security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.Security.SessionSecurity.Util.JwtUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;
import java.util.Collections;
public class CustomAuthenticationFilter extends UsernamePasswordAuthenticationFilter{

    public Authentication attemptAuthentication(HttpServletRequest httpServletRequest,
                                                HttpServletResponse httpServletResponse) throws AuthenticationException{

        AuthCredentials authCredentials = new AuthCredentials();
        try {
            authCredentials = new ObjectMapper().readValue(httpServletRequest.getReader(), AuthCredentials.class);
        }catch (IOException e){
            authCredentials.setUsername("");
            authCredentials.setPassword("");
        }

        UsernamePasswordAuthenticationToken usernamePat = new UsernamePasswordAuthenticationToken(
                authCredentials.getUsername(),
                authCredentials.getPassword(),
                Collections.emptyList()
        );
        return getAuthenticationManager().authenticate(usernamePat);
    }

    protected void successfulAuthentication(HttpServletRequest httpServletRequest,
                                            HttpServletResponse httpServletResponse,
                                            FilterChain filterChain,
                                            Authentication authentication) throws IOException, ServletException {

        JwtUtil jwtUtil = new JwtUtil();
        UserDetailsModel userDetails = (UserDetailsModel) authentication.getPrincipal();
        String token = jwtUtil.create(userDetails.getUsername(),userDetails.getFirstName());
        httpServletResponse.addHeader("Authorization", "Bearer " + token);
        httpServletResponse.getWriter().flush();
    }
}
