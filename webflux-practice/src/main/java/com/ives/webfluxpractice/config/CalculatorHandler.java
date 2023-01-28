package com.ives.webfluxpractice.config;

import com.ives.webfluxpractice.dto.Response;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Service
public class CalculatorHandler {

    public Mono<ServerResponse> plusHandler(ServerRequest serverRequest){
        int first = getValue(serverRequest,"first");
        int second = getValue(serverRequest,"second");
        return ServerResponse.ok().body(Mono.just(first+second), Response.class);
    }

    public Mono<ServerResponse> minusHandler(ServerRequest serverRequest){
        int first = getValue(serverRequest,"first");
        int second = getValue(serverRequest,"second");
        return ServerResponse.ok().body(Mono.just(first-second), Response.class);
    }


    public Mono<ServerResponse> multiplyHandler(ServerRequest serverRequest){
        int first = getValue(serverRequest,"first");
        int second = getValue(serverRequest,"second");
        return ServerResponse.ok().body(Mono.just(first*second), Response.class);
    }

    public Mono<ServerResponse> divisionHandler(ServerRequest serverRequest){
        int first = getValue(serverRequest,"first");
        int second = getValue(serverRequest,"second");
        return second != 0
                ? ServerResponse.ok().body(Mono.just(first/second), Response.class)
                : ServerResponse.badRequest().bodyValue("second can not be 0");
    }

    private int getValue(ServerRequest request, String key){
        return Integer.valueOf(request.pathVariable(key));
    }
}
