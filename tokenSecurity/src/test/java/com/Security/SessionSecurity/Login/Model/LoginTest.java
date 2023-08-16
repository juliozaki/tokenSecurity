package com.Security.SessionSecurity.Login.Model;

import com.Security.SessionSecurity.Model.Login;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LoginTest {

    @Test
    void testEmail(){
        Login login = Login.builder()
                .email("prueba@gmail.com")
                .first_name("Julio")
                .last_name("Fuentes")
                .build();
        String expected = "prueba@gmail.com";
        String actual   = login.getEmail();
        assertEquals(expected, actual);
    }

    @Test
    void testFirst_name(){
        Login login = Login.builder()
                .email("prueba@gmail.com")
                .first_name("Julio")
                .last_name("Fuentes")
                .build();
        login.setFirst_name("Julio");
        String expected = "Julio";
        String actual   = login.getFirst_name();
        assertEquals(expected, actual);
    }

    @Test
    void testLoginReference(){
        Login login1 = Login.builder()
                .email("prueba@gmail.com")
                .first_name("Julio")
                .last_name("Fuentes")
                .build();
        Login login2 = Login.builder()
                .email("prueba@gmail.com")
                .first_name("Julio")
                .last_name("Fuentes")
                .build();
        assertEquals(login1, login2);
    }
}