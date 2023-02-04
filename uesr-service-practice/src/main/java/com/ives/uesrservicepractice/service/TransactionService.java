package com.ives.uesrservicepractice.service;

import com.ives.uesrservicepractice.dto.TransactionRequestDto;
import com.ives.uesrservicepractice.dto.TransactionResponseDto;
import com.ives.uesrservicepractice.dto.TransactionStatus;
import com.ives.uesrservicepractice.entity.UserTransaction;
import com.ives.uesrservicepractice.repository.UserRepository;
import com.ives.uesrservicepractice.repository.UserTransactionRepository;
import com.ives.uesrservicepractice.util.EntityDtoUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class TransactionService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserTransactionRepository userTransactionRepository;

    public Mono<TransactionResponseDto> createTransaction(final TransactionRequestDto requestDto){
        return userRepository.updateUserBalance(requestDto.getUserId(),requestDto.getAmount())
                .filter(Boolean::booleanValue)
                .map(d -> EntityDtoUtil.toEntity(requestDto))
                .flatMap(userTransactionRepository::save)
                .map(ut -> EntityDtoUtil.toDto(requestDto, TransactionStatus.APPROVED))
                .defaultIfEmpty(EntityDtoUtil.toDto(requestDto,TransactionStatus.DECLINED));
    }

    public Flux<UserTransaction> getByUserId(Integer userId){
        return userTransactionRepository.findByUserId(userId);
    }
}
