package com.Security.SessionSecurity.Util;

import io.jsonwebtoken.*;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Component;
import java.security.Key;
import javax.crypto.spec.SecretKeySpec;

import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtUtil {

    private final static String ACCESS_TOKEN_SECRET = "EQQ63YW52G53gwsg235212SAS";
    private final static long ACCESS_TOKEN_VALIDITY_SECONDS = 2_592_000L;

    public String create(String username, String firstName) {

        if(username != null && firstName != null){
            SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
            long expirationTime = ACCESS_TOKEN_VALIDITY_SECONDS * 1000L;
            Date expirationDate = new Date(System.currentTimeMillis() + expirationTime);

            byte[] apiKeySecretBytes = ACCESS_TOKEN_SECRET.getBytes();
            Key signingKey = new SecretKeySpec(apiKeySecretBytes, signatureAlgorithm.getJcaName());

            Map<String, Object> extra = new HashMap<>();
            extra.put("firstName",firstName);

            return Jwts.builder()
                    .setId(username)
                    .setExpiration(expirationDate)
                    .addClaims(extra)
                    .signWith(signatureAlgorithm, signingKey)
                    .compact();
        }else {
            return null;
        }

    }

    public UsernamePasswordAuthenticationToken getAuthentication (String token){
        try{
            Claims claims = Jwts.parser()
                    .setSigningKey(ACCESS_TOKEN_SECRET.getBytes())
                    .parseClaimsJws(token)
                    .getBody();
            String user = claims.getId();
            return new UsernamePasswordAuthenticationToken(user, null, Collections.emptyList());
        }catch (JwtException e){
            return null;
        }
    }
}
