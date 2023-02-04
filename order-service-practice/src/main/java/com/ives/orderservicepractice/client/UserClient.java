package com.ives.orderservicepractice.client;

import com.ives.orderservicepractice.dto.ProductDto;
import com.ives.orderservicepractice.dto.TransactionRequestDto;
import com.ives.orderservicepractice.dto.TransactionResponseDto;
import com.ives.orderservicepractice.dto.UserDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class UserClient {

    WebClient webClient;

    public UserClient(@Value("${user.service.url}") String url){
        this.webClient = WebClient.builder()
                .baseUrl(url)
                .build();
    }

    public Mono<TransactionResponseDto> authorizeTransaction(TransactionRequestDto requestDto){
        return this.webClient
                .post()
                .uri("transaction")
                .bodyValue(requestDto)
                .retrieve()
                .bodyToMono(TransactionResponseDto.class);
    }

}
