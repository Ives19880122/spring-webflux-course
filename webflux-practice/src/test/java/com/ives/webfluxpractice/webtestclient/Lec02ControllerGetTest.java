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
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;

@WebFluxTest(ReactiveMathController.class)
public class Lec02ControllerGetTest {

    @Autowired
    WebTestClient client;

    @MockBean
    ReactiveMathService service;

    @Test
    public void singleResponseTest(){
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

    @Test
    public void listResponseTest(){
        // given
        Flux<Response> flux = Flux.range(1, 3)
                .map(Response::new);

        // when
        Mockito.when(service.multiplicationTable(Mockito.anyInt())).thenReturn(flux);

        // then
        client.get()
                .uri("/reactive-math/table/{number}", 5)
                .exchange()
                .expectStatus().is2xxSuccessful()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBodyList(Response.class)
                .hasSize(3);
    }

    @Test
    public void streamingResponseTest(){
        // given
        Flux<Response> flux = Flux.range(1, 3)
                .map(Response::new)
                .delayElements(Duration.ofMillis(100));
        // when
        Mockito.when(service.multiplicationTable(Mockito.anyInt())).thenReturn(flux);

        // then
        client.get()
                .uri("/reactive-math/table/{number}/stream", 5)
                .exchange()
                .expectStatus().is2xxSuccessful()
                .expectHeader().contentTypeCompatibleWith(MediaType.TEXT_EVENT_STREAM)
                .expectBodyList(Response.class)
                .hasSize(3);
    }

}
