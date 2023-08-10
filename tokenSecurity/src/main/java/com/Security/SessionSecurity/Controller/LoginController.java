package com.Security.SessionSecurity.Controller;


import com.Security.SessionSecurity.Dto.TokenDto;
import com.Security.SessionSecurity.Login.Service.LoginServiceImp;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "login")
public class LoginController {


    @RequestMapping(value = "getSession", method = RequestMethod.POST, consumes = {MediaType.APPLICATION_FORM_URLENCODED_VALUE})
    public TokenDto login(@RequestParam String email, @RequestParam String password) {
        //
        //return this.loginService.getSession(email, password);
        return new TokenDto("asi fue","nada");
    }
}
