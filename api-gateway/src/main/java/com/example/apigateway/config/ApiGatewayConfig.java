package com.example.apigateway.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApiGatewayConfig {
    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("swipe-service", r -> r
                        .path("/api/swipe/**")
                        .uri("lb://swipe-service"))
                .route("attendance-service", r -> r
                        .path("/api/attendance/**")
                        .uri("lb://attendance-service"))
                .build();
    }

}
