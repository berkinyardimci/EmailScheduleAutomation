package com.emailschedule.config;

import com.emailschedule.util.JwtManager;
import org.apache.hc.core5.http.HttpHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class AuthenticationFilter extends AbstractGatewayFilterFactory<AbstractGatewayFilterFactory.NameConfig> {
    @Autowired
    private RouteValidator validator;

    @Autowired
    private JwtManager jwtManager;

    @Override
    public GatewayFilter apply(NameConfig config) {
        return ((exchange, chain) -> {
            if (validator.isSecured.test(exchange.getRequest())) {
                if (!exchange.getRequest().getHeaders().containsKey(HttpHeaders.AUTHORIZATION)) {
                    throw new RuntimeException("missing authorization header");
                }
                String authHeader = exchange.getRequest().getHeaders().get(HttpHeaders.AUTHORIZATION).get(0);
                if (authHeader != null && authHeader.startsWith("Bearer ")) {
                    authHeader = authHeader.substring(7);
                }
                try {
                    Optional<String> idFromToken = jwtManager.getIdFromToken(authHeader);
                    exchange.getRequest()
                            .mutate()
                            .header("loggedInUserEmail",idFromToken.get());
                } catch (Exception e) {
                    System.out.println("invalid access...!");
                    throw new RuntimeException("un authorized access to application");
                }
            }
            return chain.filter(exchange);
        });
    }
}
