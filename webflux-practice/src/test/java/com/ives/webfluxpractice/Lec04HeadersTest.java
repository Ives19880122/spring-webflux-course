package com.ives.webfluxpractice;

import com.ives.webfluxpractice.dto.MultiplyRequestDto;
import com.ives.webfluxpractice.dto.Response;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

public class Lec04HeadersTest extends BaseTest{

    @Autowired
    WebClient webClient;

    @Test
    public void headersTest(){
        Mono<Response> responseMono = webClient.post()
                .uri("reactive-math/multiply")
                .bodyValue(buildRequestDto(5, 3))
                .headers(h->h.set("someKey","someVal"))
                // 方法1 設定basicAuth
                //.headers(h->h.setBasicAuth("username","password"))
                .retrieve()
                .bodyToMono(Response.class)
                .log();

        StepVerifier.create(responseMono)
                .expectNextCount(1)
                .verifyComplete();

    }

    private MultiplyRequestDto buildRequestDto(int a, int b){
        MultiplyRequestDto requestDto = new MultiplyRequestDto();
        requestDto.setFirst(a);
        requestDto.setSecond(b);
        return requestDto;
    }
}
