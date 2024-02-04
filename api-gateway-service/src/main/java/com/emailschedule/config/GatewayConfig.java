package com.emailschedule.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/*
@Configuration
public class GatewayConfig {
    @Autowired
    AuthenticationFilter filter;
    @Bean
    public RouteLocator routes(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("email-service", r -> r.path("/email/**")
                        .filters(f -> f.filter(filter))
                        .uri("http://localhost:9091"))
                .route("auth-service", r -> r.path("/auth/**")
                        .filters(f-> f.filter(filter))
                        .uri("http://localhost:9092"))
                .build();
    }
}


 */