package com.ives.productservicepractice.dto;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class ProductDto {

    private String id;
    private String description;
    private Integer price;
}