package com.Security.SessionSecurity.Security;

import com.Security.SessionSecurity.Dto.TokenDto;
import com.Security.SessionSecurity.Security.Model.AuthCredentialsModel;
import com.Security.SessionSecurity.Security.Model.UserDetailsModel;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.Security.SessionSecurity.Util.JwtUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Collections;
public class CustomAuthenticationFilter extends UsernamePasswordAuthenticationFilter{

    public Authentication attemptAuthentication(HttpServletRequest httpServletRequest,
                                                HttpServletResponse httpServletResponse) throws AuthenticationException{

        AuthCredentialsModel authCredentialsModel = new AuthCredentialsModel();
        try {
            authCredentialsModel = new ObjectMapper().readValue(httpServletRequest.getReader(), AuthCredentialsModel.class);
        }catch (IOException e){
            authCredentialsModel.setUsername("");
            authCredentialsModel.setPassword("");
        }

        UsernamePasswordAuthenticationToken usernamePat = new UsernamePasswordAuthenticationToken(
                authCredentialsModel.getUsername(),
                authCredentialsModel.getPassword(),
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
        TokenDto successful = new TokenDto(token, HttpStatus.OK.value(), "Successful authentication");
        httpServletResponse.setContentType(MediaType.APPLICATION_JSON_VALUE);
        httpServletResponse.setStatus(HttpServletResponse.SC_OK);
        OutputStream responseStream = httpServletResponse.getOutputStream();
        ObjectMapper mapper = new ObjectMapper();
        mapper.writeValue(responseStream, successful);
        responseStream.flush();
    }

    protected void unsuccessfulAuthentication(HttpServletRequest httpServletRequest,
                                              HttpServletResponse httpServletResponse,
                                              AuthenticationException failed) throws IOException, ServletException {
        //
        TokenDto unsuccessful = new TokenDto(null, HttpStatus.UNAUTHORIZED.value(), "authentication failed: wrong username or password");
        httpServletResponse.setContentType(MediaType.APPLICATION_JSON_VALUE);
        httpServletResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        OutputStream responseStream = httpServletResponse.getOutputStream();
        ObjectMapper mapper = new ObjectMapper();
        mapper.writeValue(responseStream, unsuccessful);
        responseStream.flush();
    }
}
