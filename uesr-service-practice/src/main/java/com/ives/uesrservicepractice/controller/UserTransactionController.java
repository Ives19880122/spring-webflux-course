package com.ives.uesrservicepractice.controller;

import com.ives.uesrservicepractice.dto.TransactionRequestDto;
import com.ives.uesrservicepractice.dto.TransactionResponseDto;
import com.ives.uesrservicepractice.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("user/transaction")
public class UserTransactionController {

    @Autowired
    private TransactionService transactionService;

    @PostMapping
    public Mono<TransactionResponseDto> createTransaction(@RequestBody Mono<TransactionRequestDto>requestDtoMono){
        return requestDtoMono.flatMap(transactionService::createTransaction);
    }

}
