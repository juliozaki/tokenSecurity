package com.Security.SessionSecurity.Controller;


import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "user")
public class UserController {

    @RequestMapping(value = "getUsers", method = RequestMethod.GET)
    public String getUsers() {
        return "All users here";
    }

    @RequestMapping(value = "getUsers2", method = RequestMethod.GET)
    public String getUsers2() {
        return "All users here dos dos";
    }
}
