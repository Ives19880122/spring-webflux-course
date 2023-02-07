package com.ives.webfluxpractice.webtestclient;

import com.ives.webfluxpractice.controller.ReactiveMathValidationController;
import com.ives.webfluxpractice.dto.Response;
import com.ives.webfluxpractice.service.ReactiveMathService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

@WebFluxTest(ReactiveMathValidationController.class)
public class Lec04ErrorHandlingTest {

    @Autowired
    private WebTestClient client;

    @MockBean
    private ReactiveMathService reactiveMathService;

    @Test
    public void errorHandlingTest(){

        // when
        Mockito.when(reactiveMathService.findSquare(Mockito.anyInt())).thenReturn(Mono.just(new Response(1)));

        // then
        client.get()
                .uri("/reactive-math/square/{number}/throw", 5)
                .exchange()
                .expectStatus().is4xxClientError()
                .expectBody()
                .jsonPath("$.errorCode").isEqualTo(100)
                .jsonPath("$.input").isEqualTo(5)
                .jsonPath("$.message").isEqualTo("allow range is 10 - 20");
    }



}
