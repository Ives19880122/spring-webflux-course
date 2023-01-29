package com.ives.webfluxpractice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.ClientRequest;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.ExchangeFunction;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Configuration
public class WebClientConfig {

    // 這邊設定baseurl 目前本機為8080 port
    @Bean
    public WebClient webClient(){
        return WebClient.builder()
                .baseUrl("http://localhost:8080")
                // 方法2 設定在defaultHeaders
                //.defaultHeaders(httpHeaders -> httpHeaders.setBasicAuth("username","password"))
                // 方法3 filter 建立一個交換fn把ClientRequest轉出ClientResponse
                .filter(this::sessionToken)
                .build();
    }

    private Mono<ClientResponse> sessionToken(ClientRequest request, ExchangeFunction ex){
        System.out.println("generating session token");
        ClientRequest clientRequest = ClientRequest.from(request).headers(httpHeaders -> httpHeaders.setBearerAuth("some-lengthy-jwt")).build();
        return ex.exchange(clientRequest);
    }
}
