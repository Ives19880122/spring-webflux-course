package com.ives.webfluxpractice.config;

import com.ives.webfluxpractice.dto.Response;
import com.ives.webfluxpractice.service.ReactiveMathService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class RequestHandler {

    @Autowired
    private ReactiveMathService mathService;

    public Mono<ServerResponse> squareHandler(ServerRequest serverRequest){
        // 注意要自己從字串轉型
        int input = Integer.valueOf(serverRequest.pathVariable("input"));
        Mono<Response> responseMono = mathService.findSquare(input);
        // 注意要設定回傳的型別
        return ServerResponse.ok().body(responseMono, Response.class);
    }

    public Mono<ServerResponse> tableHandler(ServerRequest serverRequest){
        int input = Integer.valueOf(serverRequest.pathVariable("input"));
        Flux<Response> responseFlux = mathService.multiplicationTable(input);
        return ServerResponse.ok().body(responseFlux, Response.class);
    }

    public Mono<ServerResponse> tableStreamHandler(ServerRequest serverRequest){
        int input = Integer.valueOf(serverRequest.pathVariable("input"));
        Flux<Response> responseFlux = mathService.multiplicationTable(input);
        return ServerResponse.ok()
                .contentType(MediaType.TEXT_EVENT_STREAM)
                .body(responseFlux, Response.class);
    }
}
