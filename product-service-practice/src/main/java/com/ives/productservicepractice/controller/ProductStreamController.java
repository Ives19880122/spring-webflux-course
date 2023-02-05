package com.ives.productservicepractice.controller;

import com.ives.productservicepractice.dto.ProductDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping("product")
public class ProductStreamController {

    @Autowired
    private Flux<ProductDto> flux;

    @GetMapping(value="stream", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<ProductDto> getProductUpdates(){
        return this.flux;
    }
}
