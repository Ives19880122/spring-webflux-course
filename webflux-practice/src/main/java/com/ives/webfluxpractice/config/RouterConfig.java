package com.ives.webfluxpractice.config;

import com.ives.webfluxpractice.dto.InputFailedValidationResponse;
import com.ives.webfluxpractice.exception.InputValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.util.function.BiFunction;

@Configuration
public class RouterConfig {

    @Autowired
    private RequestHandler requestHandler;

    @Bean
    public RouterFunction<ServerResponse> serverResponseRouterFunction(){
        return RouterFunctions.route()
                .GET("router/square/{input}",requestHandler::squareHandler)
                .GET("router/table/{input}",requestHandler::tableHandler)
                .GET("router/table/{input}/stream",requestHandler::tableStreamHandler)
                .POST("router/multiply",requestHandler::multiplyHandler)
                .GET("router/square/{input}/validation",requestHandler::squareValidationHandler)
                .onError(InputValidationException.class,exceptionHandler())
                .build();   // 最後要建立物件使用
    }

    /**
     * 建立ExceptionHandler
     * @return
     */
    private BiFunction<Throwable, ServerRequest, Mono<ServerResponse>> exceptionHandler(){
        return (err,req) -> {
            InputValidationException ex = (InputValidationException) err;
            InputFailedValidationResponse response = new InputFailedValidationResponse();
            response.setErrorCode(ex.getErrorCode());
            response.setInput(ex.getInput());
            response.setMessage(ex.getMessage());
            return ServerResponse.badRequest().bodyValue(response);
        };
    }
}
