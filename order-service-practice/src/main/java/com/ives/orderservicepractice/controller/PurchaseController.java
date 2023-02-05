package com.ives.orderservicepractice.controller;

import com.ives.orderservicepractice.dto.PurchaseOrderRequestDto;
import com.ives.orderservicepractice.dto.PurchaseOrderResponseDto;
import com.ives.orderservicepractice.service.OrderFulfillmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("order")
public class PurchaseController {

    @Autowired
    private OrderFulfillmentService orderFulfillmentService;

    @PostMapping
    public Mono<PurchaseOrderResponseDto> order(@RequestBody Mono<PurchaseOrderRequestDto> requestDtoMono){
        return orderFulfillmentService.processOrder(requestDtoMono);
    }
}
