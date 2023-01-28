package com.ives.webfluxpractice;

import com.ives.webfluxpractice.dto.Response;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@Slf4j
public class Lec01GetSingleResponseTest extends BaseTest{

    // 這邊需要建立一個專屬的Bean,故要使用Configuration
    @Autowired
    private WebClient webClient;

    @Test
    public void blockTest(){

        Response response = webClient.get()
                .uri("reactive-math/square/{number}", 5)
                .retrieve()
                .bodyToMono(Response.class)
                .block();

        System.out.println(response);

        Assertions.assertEquals(25,response.getOutput());

    }

    @Test
    public void stepVerifierTest(){

        Mono<Response> responseMono = webClient.get()
                .uri("reactive-math/square/{number}", 5)
                .retrieve()
                .bodyToMono(Response.class)
                .log();

        StepVerifier.create(responseMono)
                .expectNextMatches(val -> val.getOutput() == 25)
                .verifyComplete();

    }

}
