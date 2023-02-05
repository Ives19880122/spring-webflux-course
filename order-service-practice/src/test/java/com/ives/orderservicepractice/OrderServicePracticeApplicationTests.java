package com.ives.orderservicepractice;

import com.ives.orderservicepractice.client.ProductClient;
import com.ives.orderservicepractice.client.UserClient;
import com.ives.orderservicepractice.dto.ProductDto;
import com.ives.orderservicepractice.dto.PurchaseOrderRequestDto;
import com.ives.orderservicepractice.dto.PurchaseOrderResponseDto;
import com.ives.orderservicepractice.dto.UserDto;
import com.ives.orderservicepractice.service.OrderFulfillmentService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@SpringBootTest
class OrderServicePracticeApplicationTests {

	@Autowired
	ProductClient productClient;

	@Autowired
	UserClient userClient;

	@Autowired
	OrderFulfillmentService service;

	@Test
	void contextLoads() {

		Flux<PurchaseOrderResponseDto> dtoFlux = Flux.zip(userClient.getAllUsers(), productClient.getAllProducts())
				.map(t -> buildDto(t.getT1(), t.getT2()))
				.flatMap(dto -> service.processOrder(Mono.just(dto)))
				.doOnNext(System.out::println);

		StepVerifier.create(dtoFlux)
				.expectNextCount(4)
				.verifyComplete();
	}

	private PurchaseOrderRequestDto buildDto(UserDto userDto, ProductDto productDto){
		PurchaseOrderRequestDto dto = new PurchaseOrderRequestDto();
		dto.setUserId(userDto.getId());
		dto.setProductId(productDto.getId());
		return dto;
	}

}
