package com.emailschedule.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Optional;

@Component
public class JwtManager {

    String secretKey = "secretKey";

    String issuer = "scheduleEmail";

    public Optional<String> getEmailFromToken(String token){

        Algorithm algorithm = Algorithm.HMAC512(secretKey);
        JWTVerifier verifier = JWT.require(algorithm).withIssuer(issuer).build();

        DecodedJWT decodedJWT = verifier.verify(token);
        if(decodedJWT == null){
            throw new RuntimeException("Token Hatası");
        }
        String email = decodedJWT.getClaim("email").asString();
        return Optional.of(email);
    }
}
