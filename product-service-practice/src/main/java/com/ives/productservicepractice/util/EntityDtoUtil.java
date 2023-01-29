package com.ives.productservicepractice.util;

import com.ives.productservicepractice.dto.ProductDto;
import com.ives.productservicepractice.entity.Product;
import org.springframework.beans.BeanUtils;

public class EntityDtoUtil {

    /**
     * 資料從Entity -> Dto
     * @param product
     * @return
     */
    public static ProductDto toDto(Product product){
        ProductDto dto = new ProductDto();
        // 一種方式可以把相同的屬性名稱資料進行複製轉換
        BeanUtils.copyProperties(product,dto);
        return dto;
    }

    /**
     * 資料從Dto -> Entity
     * @param productDto
     * @return
     */
    public static Product toEntity(ProductDto productDto){
        Product entity = new Product();
        BeanUtils.copyProperties(productDto,entity);
        return entity;
    }
}
