package com.ives.webfluxpractice.config;

import com.ives.webfluxpractice.dto.InputFailedValidationResponse;
import com.ives.webfluxpractice.exception.InputValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.*;
import reactor.core.publisher.Mono;

import java.util.function.BiFunction;

@Configuration
public class RouterConfig {

    @Autowired
    private RequestHandler requestHandler;

    /**
     * 修改使用highLevelRouter註冊
     * path的用途可以整理對應的router function
     * @return
     */
    @Bean
    public RouterFunction<ServerResponse> highLevelRouter(){
        return RouterFunctions.route()
                .path("router",this::serverResponseRouterFunction)
                .build();
    }

    public RouterFunction<ServerResponse> serverResponseRouterFunction(){
        return RouterFunctions.route()
                // 使用路徑的Regex檢核輸入值
                .GET("square/{input}", RequestPredicates.path("*/1?").or(RequestPredicates.path("*/20")),requestHandler::squareHandler)
                .GET("square/{input}",req->ServerResponse.badRequest().bodyValue("only 10-20 allow"))
                .GET("table/{input}",requestHandler::tableHandler)
                .GET("table/{input}/stream",requestHandler::tableStreamHandler)
                .POST("multiply",requestHandler::multiplyHandler)
                .GET("square/{input}/validation",requestHandler::squareValidationHandler)
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
