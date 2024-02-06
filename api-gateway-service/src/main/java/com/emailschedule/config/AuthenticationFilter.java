package com.emailschedule.config;

import com.emailschedule.client.AuthServiceClient;
import com.emailschedule.util.JwtManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.context.annotation.Lazy;
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
    }

    private final RouteValidator validator;
    private AuthServiceClient authServiceClient;

    @Autowired
    public void setAuthServiceClient(AuthServiceClient authServiceClient) {
        this.authServiceClient = authServiceClient;
    }

    public AuthenticationFilter(RouteValidator validator) {
        super(Config.class);
        this.validator = validator;
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
                    Optional<String> emailFromToken = authServiceClient.getEmailFromToken(token);
                    if (!emailFromToken.isPresent()) {
                        return onError(exchange, HttpStatus.UNAUTHORIZED);
                    }
                    populateRequestWithHeaders(exchange, emailFromToken.get());
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
}


