package com.ives.webfluxpractice.webtestclient;

import com.ives.webfluxpractice.controller.ReactiveMathController;
import com.ives.webfluxpractice.dto.Response;
import com.ives.webfluxpractice.service.ReactiveMathService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

@WebFluxTest(ReactiveMathController.class)
public class Lec02ControllerGetTest {

    @Autowired
    WebTestClient client;

    @MockBean
    ReactiveMathService service;

    @Test
    public void fluentAssertionTest(){
        // 使用Mockito進行Service呼叫方法的模擬
        Mockito.when(service.findSquare(Mockito.anyInt())).thenReturn(Mono.just(new Response(25)));

        client.get()
                .uri("/reactive-math/square/{number}", 5)
                .exchange()
                .expectStatus().is2xxSuccessful()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody(Response.class)
                .value(res-> Assertions.assertThat(res.getOutput()).isEqualTo(25));
    }

}
