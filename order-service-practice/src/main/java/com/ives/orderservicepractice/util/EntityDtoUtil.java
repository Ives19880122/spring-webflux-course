package com.ives.orderservicepractice.util;

import com.ives.orderservicepractice.dto.*;
import com.ives.orderservicepractice.entity.PurchaseOrder;
import org.springframework.beans.BeanUtils;

public class EntityDtoUtil {

    public static void setTransactionRequestDto(RequestContext requestContext){
        PurchaseOrderRequestDto purchaseOrderRequestDto = requestContext.getPurchaseOrderRequestDto();
        ProductDto productDto = requestContext.getProductDto();
        TransactionRequestDto dto = new TransactionRequestDto();
        dto.setUserId(purchaseOrderRequestDto.getUserId());
        dto.setAmount(productDto.getPrice());
        requestContext.setTransactionRequestDto(dto);
    }

    public static PurchaseOrder getPurchaseOrder(RequestContext requestContext){
        TransactionResponseDto transactionResponseDto = requestContext.getTransactionResponseDto();
        ProductDto productDto = requestContext.getProductDto();
        PurchaseOrder purchaseOrder = new PurchaseOrder();
        purchaseOrder.setAmount(transactionResponseDto.getAmount());
        purchaseOrder.setUserId(transactionResponseDto.getUserId());
        purchaseOrder.setProductId(productDto.getId());
        OrderStatus status = TransactionStatus.APPROVED.equals(transactionResponseDto.getStatus())
                ? OrderStatus.COMPLETED
                : OrderStatus.FAILED;
        purchaseOrder.setStatus(status);
        return purchaseOrder;
    }

    /**
     * 使用BeanUtil轉換Entity to Dto
     * @param purchaseOrder
     * @return
     */
    public static PurchaseOrderResponseDto getPurchaseOrderResponseDto(PurchaseOrder purchaseOrder){
        PurchaseOrderResponseDto responseDto = new PurchaseOrderResponseDto();
        BeanUtils.copyProperties(purchaseOrder,responseDto);
        return responseDto;
    }
}
