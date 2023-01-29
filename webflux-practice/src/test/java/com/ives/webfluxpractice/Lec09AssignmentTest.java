package com.ives.webfluxpractice;

import com.ives.webfluxpractice.dto.Response;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

public class Lec09AssignmentTest extends BaseTest{

    @Autowired
    WebClient webClient;
    private static final int FIRST_NUMBER = 10;
    private static final int[] SECOND_NUMBERS = {1,2,3,4,5};

    @Test
    public void plusTest(){
        for (int val: SECOND_NUMBERS) {
            Mono<Response> responseMono = webClient.get()
                    .uri(u -> u.path("calculator/{first}/{second}").build(FIRST_NUMBER, val))
                    .headers(httpHeaders -> httpHeaders.set("OP","+"))
                    .retrieve()
                    .bodyToMono(Response.class)
                    .log();

            StepVerifier.create(responseMono)
                    .expectNextCount(1)
                    .verifyComplete();
        }
    }

    @Test
    public void minusTest(){
        for (int val: SECOND_NUMBERS) {
            Mono<Response> responseMono = webClient.get()
                    .uri(u -> u.path("calculator/{first}/{second}").build(FIRST_NUMBER, val))
                    .headers(httpHeaders -> httpHeaders.set("OP","-"))
                    .retrieve()
                    .bodyToMono(Response.class)
                    .log();

            StepVerifier.create(responseMono)
                    .expectNextCount(1)
                    .verifyComplete();
        }
    }

    @Test
    public void multiplyTest(){
        for (int val: SECOND_NUMBERS) {
            Mono<Response> responseMono = webClient.get()
                    .uri(u -> u.path("calculator/{first}/{second}").build(FIRST_NUMBER, val))
                    .headers(httpHeaders -> httpHeaders.set("OP","*"))
                    .retrieve()
                    .bodyToMono(Response.class)
                    .log();

            StepVerifier.create(responseMono)
                    .expectNextCount(1)
                    .verifyComplete();
        }
    }

    @Test
    public void divisionTest(){
        for (int val: SECOND_NUMBERS) {
            Mono<Response> responseMono = webClient.get()
                    .uri(u -> u.path("calculator/{first}/{second}").build(FIRST_NUMBER, val))
                    .headers(httpHeaders -> httpHeaders.set("OP","/"))
                    .retrieve()
                    .bodyToMono(Response.class)
                    .log();

            StepVerifier.create(responseMono)
                    .expectNextCount(1)
                    .verifyComplete();
        }
    }

}
