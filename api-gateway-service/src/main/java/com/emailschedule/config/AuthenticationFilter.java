package com.emailschedule.config;

import com.emailschedule.util.JwtManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Optional;


@Component
public class AuthenticationFilter extends AbstractGatewayFilterFactory<AuthenticationFilter.Config> {

    public static class Config {
        // Eğer ihtiyacınız varsa, yapılandırma seçeneklerini buraya ekleyebilirsiniz
    }

    private final RouteValidator validator;
    private final JwtManager jwtManager;

    public AuthenticationFilter(RouteValidator validator, JwtManager jwtManager) {
        super(Config.class);
        this.validator = validator;
        this.jwtManager = jwtManager;
    }

    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {
            ServerHttpRequest request = exchange.getRequest();

            if (validator.isSecured.test(request)) {
                if (authMissing(request)) {
                    return onError(exchange, HttpStatus.UNAUTHORIZED);
                }

                List<String> authHeaders = request.getHeaders().get("Authorization");
                String authHeader = authHeaders != null ? authHeaders.get(0) : null;
                if (authHeader != null && authHeader.startsWith("Bearer ")) {
                    String token = authHeader.substring(7);
                    //autha istek Feign ile
                    Optional<String> idFromToken = jwtManager.getEmailFromToken(token);
                    if (!idFromToken.isPresent()) {
                        return onError(exchange, HttpStatus.UNAUTHORIZED);
                    }
                    populateRequestWithHeaders(exchange, idFromToken.get());
                }
            }
            return chain.filter(exchange);
        };
    }

    private Mono<Void> onError(ServerWebExchange exchange, HttpStatus httpStatus) {
        ServerHttpResponse response = exchange.getResponse();
        response.setStatusCode(httpStatus);
        return response.setComplete();
    }

    private boolean authMissing(ServerHttpRequest request) {
        HttpHeaders headers = request.getHeaders();
        return !headers.containsKey(HttpHeaders.AUTHORIZATION) || headers.get(HttpHeaders.AUTHORIZATION).isEmpty();
    }

    private void populateRequestWithHeaders(ServerWebExchange exchange, String email) {
        exchange.getRequest().mutate()
                .header("loggedInEmail", email)
                .build();
    }
    /*
    @Autowired
    private RouteValidator validator;

    @Autowired
    private JwtManager jwtManager;


    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();

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

     */
}


