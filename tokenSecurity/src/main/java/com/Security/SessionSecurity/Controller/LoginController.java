package com.Security.SessionSecurity.Controller;


import com.Security.SessionSecurity.Dto.TokenDto;
import com.Security.SessionSecurity.Security.Model.AuthCredentialsModel;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {


    @RequestMapping(value = "login", method = RequestMethod.POST, consumes = {MediaType.APPLICATION_FORM_URLENCODED_VALUE})
    public TokenDto login(@RequestParam AuthCredentialsModel credentials) {
        //
        //return this.loginService.getSession(email, password);
        return new TokenDto("asi fue",200, "nada de nada");
    }
}
