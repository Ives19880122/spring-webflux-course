package com.ives.orderservicepractice.service;

import com.ives.orderservicepractice.dto.PurchaseOrderResponseDto;
import com.ives.orderservicepractice.entity.PurchaseOrder;
import com.ives.orderservicepractice.repository.PurchaseOrderRepository;
import com.ives.orderservicepractice.util.EntityDtoUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

import java.util.List;

@Service
public class OrderQueryService {

    @Autowired
    private PurchaseOrderRepository orderRepository;

    public Flux<PurchaseOrderResponseDto> getProductByUserId(int userId){
        return Flux.fromStream(()->orderRepository.findByUserId(userId).stream()) // blocking
                .map(EntityDtoUtil::getPurchaseOrderResponseDto)
                .subscribeOn(Schedulers.boundedElastic());
    }

}
