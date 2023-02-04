package com.ives.uesrservicepractice.controller;

import com.ives.uesrservicepractice.dto.TransactionRequestDto;
import com.ives.uesrservicepractice.dto.TransactionResponseDto;
import com.ives.uesrservicepractice.entity.UserTransaction;
import com.ives.uesrservicepractice.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
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

    @GetMapping
    public Flux<UserTransaction> getByUserId(@RequestParam("userId") Integer userId){
        return transactionService.getByUserId(userId);
    }
}
