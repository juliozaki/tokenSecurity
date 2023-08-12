package com.Security.SessionSecurity.Security;

import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.Calendar;

import org.springframework.stereotype.Component;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

@Component
public class JwtUtil {
    private RSAPrivateKey privateKey;
    private RSAPublicKey publicKey;

    public JwtUtil(RSAPublicKey publicKey, RSAPrivateKey privateKey) {
        this.privateKey = privateKey;
        this.publicKey = publicKey;
    }


    public String encode(String subject) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH, 1);
        return JWT.create()
                .withSubject(subject)
                .withExpiresAt(calendar.getTime())
                .sign(Algorithm.RSA256(publicKey, privateKey) );
    }
}
