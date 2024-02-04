package com.emailschedule.config;

import com.emailschedule.util.JwtManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.Optional;


@Component
public class AuthenticationFilter implements GatewayFilter {
    @Autowired
    private RouteValidator validator;

    @Autowired
    private JwtManager jwtManager;


    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();

        System.out.println("validator.isSecured.test(request) " + validator.isSecured.test(request) );
        if (validator.isSecured.test(request)) {
            if (authMissing(request)) {
                return onError(exchange, HttpStatus.UNAUTHORIZED);
            }

            String authHeader = request.getHeaders().getOrEmpty("Authorization").get(0);
            if (authHeader != null && authHeader.startsWith("Bearer ")) {
                authHeader = authHeader.substring(7);
            }

            System.out.println(authHeader);

            Optional<String> idFromToken = jwtManager.getEmailFromToken(authHeader);
            System.out.println(idFromToken.get());
            if (!idFromToken.isPresent()) {
                return onError(exchange, HttpStatus.UNAUTHORIZED);
            }
            populateRequestWithHeaders(exchange, idFromToken.get());
        }
        return chain.filter(exchange);
    }

    private Mono<Void> onError(ServerWebExchange exchange, HttpStatus httpStatus) {
        ServerHttpResponse response = exchange.getResponse();
        response.setStatusCode(httpStatus);
        return response.setComplete();
    }

    private boolean authMissing(ServerHttpRequest request) {
        return !request.getHeaders().containsKey("Authorization");
    }

    private void populateRequestWithHeaders(ServerWebExchange exchange, String email) {
        exchange.getRequest().mutate()
                .header("loggedInEmail", email)
                .build();
    }
}


