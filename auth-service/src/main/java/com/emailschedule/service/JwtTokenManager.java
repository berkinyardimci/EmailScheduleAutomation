package com.emailschedule.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.emailschedule.entity.Auth;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Service
public class JwtTokenManager {

    String secretKey = "secretKey";

    String issuer = "scheduleEmail";

    public Optional<String> createToken(Auth auth) {
        String token = null;
        Date date = new Date(System.currentTimeMillis() + (1000 * 60 * 30));
        //String[] roles = auth.getRoles().stream().map(x -> x.toString()).toArray(String[]::new);
        try {
            token = JWT.create()
                    .withIssuer(issuer)
                    .withClaim("id", auth.getId())
                    .withClaim("email", auth.getEmail())
                    //.withArrayClaim("roles", roles)
                    .withIssuedAt(new Date())
                    .withExpiresAt(date)
                    .sign(Algorithm.HMAC512(secretKey));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return Optional.ofNullable(token);
    }
}