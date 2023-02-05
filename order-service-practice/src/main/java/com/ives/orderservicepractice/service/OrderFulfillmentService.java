package com.ives.orderservicepractice.service;

import com.ives.orderservicepractice.client.ProductClient;
import com.ives.orderservicepractice.client.UserClient;
import com.ives.orderservicepractice.dto.PurchaseOrderRequestDto;
import com.ives.orderservicepractice.dto.PurchaseOrderResponseDto;
import com.ives.orderservicepractice.dto.RequestContext;
import com.ives.orderservicepractice.repository.PurchaseOrderRepository;
import com.ives.orderservicepractice.util.EntityDtoUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;
import reactor.util.retry.Retry;

import java.time.Duration;

@Service
public class OrderFulfillmentService {

    @Autowired
    private PurchaseOrderRepository orderRepository;

    @Autowired
    private ProductClient productClient;

    @Autowired
    private UserClient userClient;

    public Mono<PurchaseOrderResponseDto> processOrder(Mono<PurchaseOrderRequestDto> requestDtoMono){
        return requestDtoMono.map(RequestContext::new)
                .flatMap(this::productRequestResponse)
                .doOnNext(EntityDtoUtil::setTransactionRequestDto)
                .flatMap(this::userRequestResponse)
                .map(EntityDtoUtil::getPurchaseOrder)
                .map(orderRepository::save) // blocking 需要相應的對策
                .map(EntityDtoUtil::getPurchaseOrderResponseDto)
                .subscribeOn(Schedulers.boundedElastic());
    }

    /**
     * 1. 從RequestContext中取得資料
     * 2. 使用productClient取商品
     * 3. 更新Context的商品資料
     * 4. 回傳RequestContext
     * @param rc
     * @return
     */
    private Mono<RequestContext> productRequestResponse(RequestContext rc){
        PurchaseOrderRequestDto purchaseOrderRequestDto = rc.getPurchaseOrderRequestDto();
        return productClient.getProductById(purchaseOrderRequestDto.getProductId())
                .doOnNext(rc::setProductDto)
                .retryWhen(Retry.fixedDelay(5, Duration.ofSeconds(1)))
                .thenReturn(rc);
    }

    /**
     * 1. 傳入RequestContext
     * 2. 呼叫authorizeTransaction
     * 3. 從context取得transactionRequestDto
     * 4. 最終回傳RequestContext
     * @param rc
     * @return
     */
    private Mono<RequestContext> userRequestResponse(RequestContext rc){
        return userClient.authorizeTransaction(rc.getTransactionRequestDto())
                .doOnNext(rc::setTransactionResponseDto)
                .thenReturn(rc);
    }

}
