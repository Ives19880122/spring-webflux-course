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

    /**
    private Mono<ClientResponse> sessionToken(ClientRequest request, ExchangeFunction ex){
        System.out.println("generating session token");
        ClientRequest clientRequest = ClientRequest.from(request).headers(httpHeaders -> httpHeaders.setBearerAuth("some-lengthy-jwt")).build();
        return ex.exchange(clientRequest);
    }*/

    /**
     * 改寫交換機制
     * @param request
     * @param ex
     * @return
     */
    private Mono<ClientResponse> sessionToken(ClientRequest request, ExchangeFunction ex){
        ClientRequest clientRequest = request.attribute("auth")
                .map(v -> "basic".equals(v)
                        ? withBasicAuth(request)
                        : withOAuth(request)
                ).orElse(request);
        return ex.exchange(clientRequest);
    }

    /**
     * 如果是auth basic時會被引導至此驗證
     * @param request
     * @return
     */
    private ClientRequest withBasicAuth(ClientRequest request){
        System.out.println("Basic Auth");
        return ClientRequest.from(request)
                .headers(h->h.setBasicAuth("username","password"))
                .build();
    }

    /**
     * 如果是auth 不為basic時會執行OAuth驗證
     * @param request
     * @return
     */
    private ClientRequest withOAuth(ClientRequest request){
        System.out.println("OAuth");
        return ClientRequest.from(request)
                .headers(h->h.setBearerAuth("some-token"))
                .build();
    }
}
