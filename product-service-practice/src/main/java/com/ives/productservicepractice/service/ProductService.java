package com.ives.productservicepractice.service;

import com.ives.productservicepractice.dto.ProductDto;
import com.ives.productservicepractice.repository.ProductRepository;
import com.ives.productservicepractice.util.EntityDtoUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Range;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Sinks;

@Service
public class ProductService {

    @Autowired
    private ProductRepository repository;

    @Autowired
    private Sinks.Many<ProductDto> sink;

    public Flux<ProductDto> getAll(){
        return repository.findAll()
                .map(EntityDtoUtil::toDto);
    }

    public Mono<ProductDto> getProductById(String id){
        return repository.findById(id)
                .map(EntityDtoUtil::toDto);
    }

    public Mono<ProductDto> insertProduct(Mono<ProductDto> productDtoMono){
        return productDtoMono
                .map(EntityDtoUtil::toEntity)   // insert前轉換為Entity
                .flatMap(repository::insert)    // 回傳是Mono要用flatMap
                .map(EntityDtoUtil::toDto)      // insert後轉換為Dto
                .doOnNext(this.sink::tryEmitNext);
    }

    public Mono<ProductDto> updateProduct(String id, Mono<ProductDto> productDtoMono){
        return repository.findById(id)
                .flatMap(p -> productDtoMono
                        .map(EntityDtoUtil::toEntity)
                        .doOnNext(e -> e.setId(id)))    // 處理是Mono要用flatMap
                .flatMap(repository::save)              // 回傳是Mono要用flatMap
                .map(EntityDtoUtil::toDto);             // update後轉換為Dto
    }

    public Mono<Void> deleteProduct(String id){
        return repository.deleteById(id);               // 要回傳Mono包裹的Void
    }

    public Flux<ProductDto> getProductByPriceRange(int min, int max){
        return repository.findByPriceBetween(Range.closed(min,max))
                .map(EntityDtoUtil::toDto);
    }
}
