package com.ives.webfluxpractice.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.*;

@Configuration
public class CalculatorRouterConfig {

    @Autowired
    private CalculatorHandler calculatorHandler;


    @Bean
    public RouterFunction<ServerResponse> highLevelCalculatorRouter(){
        return RouterFunctions.route()
                .path("calculator",this::serverResponseRouterFunction)
                .build();
    }

    public RouterFunction<ServerResponse> serverResponseRouterFunction(){
        String pathPatten = "{first}/{second}";
        return RouterFunctions.route()
                .GET(pathPatten, isOperation("+"),calculatorHandler::plusHandler)
                .GET(pathPatten, isOperation("-"),calculatorHandler::minusHandler)
                .GET(pathPatten, isOperation("*"),calculatorHandler::multiplyHandler)
                .GET(pathPatten, isOperation("/"),calculatorHandler::divisionHandler)
                .build();
    }

    private RequestPredicate isOperation(String operation) {
        return RequestPredicates.headers(headers ->
                operation.equals(headers.asHttpHeaders()
                .toSingleValueMap()
                .get("OP")));
    }
}
