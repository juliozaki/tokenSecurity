package com.Security.SessionSecurity.Service;

import com.Security.SessionSecurity.Model.Login;
import com.Security.SessionSecurity.Repository.LoginRepository;
import com.Security.SessionSecurity.Security.Model.UserDetailsModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


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
        return new UserDetailsModel(login);
    }
}
