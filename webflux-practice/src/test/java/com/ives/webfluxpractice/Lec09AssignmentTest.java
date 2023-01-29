package com.ives.webfluxpractice;

import com.ives.webfluxpractice.dto.Response;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.Arrays;

public class Lec09AssignmentTest extends BaseTest{

    @Autowired
    WebClient webClient;
    private static final int FIRST_NUMBER = 10;
    private static final String FORMAT = "%d %s %d = %s";

    @Test
    public void plusTest(){
        // 改用Flux range
        Flux<String> stringFlux = Flux.range(1, 5)
                .flatMap(v -> send(v, "+"))
                .log();

        StepVerifier.create(stringFlux)
                .expectNextCount(5)
                .verifyComplete();
    }


    @Test
    public void minusTest(){
        Flux<String> stringFlux = Flux.range(1, 5)
                .flatMap(v -> send(v, "-"))
                .log();

        StepVerifier.create(stringFlux)
                .expectNextCount(5)
                .verifyComplete();
    }

    @Test
    public void multiplyTest(){
        Flux<String> stringFlux = Flux.range(1, 5)
                .flatMap(v -> send(v, "*"))
                .log();

        StepVerifier.create(stringFlux)
                .expectNextCount(5)
                .verifyComplete();
    }

    @Test
    public void divisionTest(){
        Flux<String> stringFlux = Flux.range(1, 5)
                .flatMap(v -> send(v, "/"))
                .log();

        StepVerifier.create(stringFlux)
                .expectNextCount(5)
                .verifyComplete();
    }

    /**
     * 如果要照著順序顯示可改用flatMapSequential
     */
    @Test
    public void test(){
        Flux<String> stringFlux = Flux.range(1, 5)
                .flatMap(v -> Flux
                        .just("+","-","*","/")
                        .flatMap(op->send(v,op)))
                .doOnNext(System.out::println);

        StepVerifier.create(stringFlux)
                .expectNextCount(20)
                .verifyComplete();
    }

    private Mono<String> send(int sec, String operation) {
        return webClient.get()
                .uri(u -> u.path("calculator/{first}/{second}").build(FIRST_NUMBER, sec))
                .headers(httpHeaders -> httpHeaders.set("OP", operation))
                .retrieve()
                .bodyToMono(String.class)
                .map(result -> String.format(FORMAT, FIRST_NUMBER, operation, sec, result));
    }
}
