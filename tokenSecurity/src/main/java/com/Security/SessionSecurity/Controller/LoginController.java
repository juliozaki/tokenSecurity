package com.Security.SessionSecurity.Controller;


import com.Security.SessionSecurity.Dto.TokenDto;
import com.Security.SessionSecurity.Login.Service.LoginService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.awt.*;

@RestController
@RequestMapping(value = "login")
public class LoginController {
    private LoginService loginService;

    public LoginController(LoginService loginService) {
        this.loginService = loginService;
    }

    @RequestMapping(value = "getSession", method = RequestMethod.POST, consumes = {MediaType.APPLICATION_FORM_URLENCODED_VALUE})
    public TokenDto login(@RequestParam String username, @RequestParam String password) {
        //
        return this.loginService.login(username, password);
    }
}
