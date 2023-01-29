package com.ives.productservicepractice.repository;

import com.ives.productservicepractice.entity.Product;
import org.springframework.data.domain.Range;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface ProductRepository extends ReactiveMongoRepository<Product,String> {

    // > min & < main
    // Flux<Product> findByPriceBetween(int min, int max);
    Flux<Product> findByPriceBetween(Range<Integer> range); // 改用Range
}
