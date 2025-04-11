package com.ramo.iFootballgateway.Filter;


import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component
public class CustomGlobalFilter implements GlobalFilter, Ordered {
    private static final Logger logger = LoggerFactory.getLogger(CustomGlobalFilter.class);

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        // Pre-processing logic
        logger.info("Path of the request received: {}",
                exchange.getRequest().getPath());

        // Add custom headers if needed
        exchange.getRequest().mutate()
                .header("X-Gateway-Timestamp", String.valueOf(System.currentTimeMillis()))
                .build();

        // Continue the filter chain
        return chain.filter(exchange)
                .then(Mono.fromRunnable(() -> {
                    // Post-processing logic
                    logger.info("Response status: {}",
                            exchange.getResponse().getStatusCode());
                }));
    }

    @Override
    public int getOrder() {
        return -1; // Highest precedence
    }
}
