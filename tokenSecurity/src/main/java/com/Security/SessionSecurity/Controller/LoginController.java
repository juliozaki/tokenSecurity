package com.Security.SessionSecurity.Controller;


import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "login")
public class LoginController {
    @RequestMapping(value = "getSession", method = RequestMethod.GET)
    public String login() {
        return "login";
    }
}
