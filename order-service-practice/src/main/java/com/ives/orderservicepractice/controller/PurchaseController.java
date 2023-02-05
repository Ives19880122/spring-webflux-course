package com.ives.orderservicepractice.controller;

import com.ives.orderservicepractice.dto.PurchaseOrderRequestDto;
import com.ives.orderservicepractice.dto.PurchaseOrderResponseDto;
import com.ives.orderservicepractice.service.OrderFulfillmentService;
import com.ives.orderservicepractice.service.OrderQueryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("order")
public class PurchaseController {

    @Autowired
    private OrderFulfillmentService orderFulfillmentService;

    @Autowired
    private OrderQueryService queryService;

    @PostMapping
    public Mono<PurchaseOrderResponseDto> order(@RequestBody Mono<PurchaseOrderRequestDto> requestDtoMono){
        return orderFulfillmentService.processOrder(requestDtoMono);
    }

    @GetMapping("user/{userId}")
    public Flux<PurchaseOrderResponseDto> getOrderByUserId(@PathVariable int userId){
        return queryService.getProductByUserId(userId);
    }

}
