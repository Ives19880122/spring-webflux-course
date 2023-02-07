package com.ives.webfluxpractice.webtestclient;

import com.ives.webfluxpractice.config.RequestHandler;
import com.ives.webfluxpractice.config.RouterConfig;
import com.ives.webfluxpractice.dto.Response;
import com.ives.webfluxpractice.service.ReactiveMathService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@WebFluxTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ContextConfiguration(classes=RouterConfig.class)
public class Lec05RouterFunctionTest {

    private WebTestClient client;

    @Autowired
    private RouterConfig config;

    @Autowired
    private ApplicationContext context;

    @MockBean
    private RequestHandler handler;

    @BeforeAll
    public void setClient(){
        // 1. 使用config注入來建立client
        // client = WebTestClient.bindToRouterFunction(config.highLevelRouter()).build();
        // 2. 使用ctx注入來建立client
        client = WebTestClient.bindToApplicationContext(context).build();
        // 3. 綁定Server的方式建立webclient 連線
        /*
         * client = WebTestClient.bindToServer().baseUrl("http://localhost:8080/").build()
         *      .get()...;
         */
    }

    @Test
    public void test(){
        Mockito.when(handler.squareHandler(Mockito.any())).thenReturn(ServerResponse.ok().bodyValue(new Response(100)));

        client.get()
                .uri("/router/square/{input}",10)
                .exchange()
                .expectStatus().is2xxSuccessful()
                .expectBody(Response.class)
                .value(response -> Assertions.assertThat(response.getOutput()).isEqualTo(100));

    }



}
