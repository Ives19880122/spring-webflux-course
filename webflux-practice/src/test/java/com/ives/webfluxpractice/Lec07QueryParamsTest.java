package com.ives.webfluxpractice;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriBuilder;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.net.URI;
import java.util.Map;
import java.util.function.Function;

public class Lec07QueryParamsTest extends BaseTest{

    @Autowired
    WebClient webClient;

    String queryString = "http://localhost:8080/jobs/search?count={count}&page={page}";

    @Test
    public void queryParamsTest(){

        // 類型1 使用UriComponentsBuilder
        URI uri = UriComponentsBuilder.fromUriString(queryString)
                .build(10,20);

        // 類型2 使用UriBuilder
        Function<UriBuilder, URI> uriBuilderURIFunction = uriBuilder -> uriBuilder.path("jobs/search").query("count={count}&page={page}").build(10, 20);

        // 類型3 使用Map.of提供params
        Map<String, Integer> params = Map.of(
                "count", 10,
                "page", 20
        );
        // 改用Map提供查詢對應的參數
        Function<UriBuilder, URI> uriBuilderURIFunctionUsingParams = uriBuilder -> uriBuilder.path("jobs/search").query("count={count}&page={page}").build(params);

        Flux<Integer> integerFlux = webClient
                .get()
                //.uri(uri)
                //.uri(uriBuilderURIFunction)
                .uri(uriBuilderURIFunctionUsingParams)
                .retrieve()
                .bodyToFlux(Integer.class)
                .doOnNext(System.out::println);

        StepVerifier.create(integerFlux)
                .expectNextCount(2)
                .verifyComplete();

    }

}
