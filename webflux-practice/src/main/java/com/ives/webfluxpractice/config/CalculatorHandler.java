package com.ives.webfluxpractice.config;

import com.ives.webfluxpractice.dto.Response;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.util.function.BiFunction;

@Service
public class CalculatorHandler {

    public Mono<ServerResponse> plusHandler(ServerRequest serverRequest){
        return process(serverRequest, (first,second) -> ServerResponse.ok().body(Mono.just(first+second), Response.class));
    }

    public Mono<ServerResponse> minusHandler(ServerRequest serverRequest){
        return process(serverRequest, (first,second) -> ServerResponse.ok().body(Mono.just(first-second), Response.class));
    }


    public Mono<ServerResponse> multiplyHandler(ServerRequest serverRequest){
        return process(serverRequest, (first,second) -> ServerResponse.ok().body(Mono.just(first*second), Response.class));
    }

    public Mono<ServerResponse> divisionHandler(ServerRequest serverRequest){
        return process(serverRequest, (first,second) -> {
            return second != 0
                ? ServerResponse.ok().body(Mono.just(first/second), Response.class)
                : ServerResponse.badRequest().bodyValue("second can not be 0");
        });
    }

    /**
     * 抽出重複的執行邏輯
     * @param serverRequest
     * @param opLogic
     * @return
     */
    public Mono<ServerResponse> process(ServerRequest serverRequest,
                                        BiFunction<Integer,Integer,Mono<ServerResponse>> opLogic){
        int first = getValue(serverRequest,"first");
        int second = getValue(serverRequest,"second");
        return opLogic.apply(first,second);
    }

    private int getValue(ServerRequest request, String key){
        return Integer.valueOf(request.pathVariable(key));
    }
}
