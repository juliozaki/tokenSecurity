package com.Security.SessionSecurity.Login.Service;

import com.Security.SessionSecurity.Login.Model.Login;
import com.Security.SessionSecurity.Login.Repository.LoginRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;


@Component
@Service
public class LoginServiceImp implements UserDetailsService {
    @Autowired
    private LoginRepository loginRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException{
        final Login login = loginRepository.findByEmail(email);
        if (login == null) {
            throw new UsernameNotFoundException(email);
        }
        return User.withUsername(login.getEmail())
                .password(login.getPassword())
                .roles("USER")
                .build();
    }
}
