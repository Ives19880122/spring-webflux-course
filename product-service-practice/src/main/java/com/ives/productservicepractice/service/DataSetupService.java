package com.ives.productservicepractice.service;

import com.ives.productservicepractice.dto.ProductDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class DataSetupService implements CommandLineRunner {

    @Autowired
    ProductService service;

    @Override
    public void run(String... args) throws Exception {
        ProductDto p1 = new ProductDto("4k-tv",1000);
        ProductDto p2 = new ProductDto("headphone",100);
        ProductDto p3 = new ProductDto("mac pro",3000);
        ProductDto p4 = new ProductDto("pixel",650);

        Flux.just(p1,p2,p3,p4)
                .flatMap(productDto -> service.insertProduct(Mono.just(productDto)))
                .subscribe(System.out::println);
    }
}