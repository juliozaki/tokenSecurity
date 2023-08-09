package com.Security.SessionSecurity.Controller;


import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "user")
public class UserController {

    @RequestMapping(value = "getUsers", method = RequestMethod.GET)
    public String login() {
        return "All users here";
    }
}
