package com.ives.webfluxpractice.controller;

import com.ives.webfluxpractice.dto.Response;
import com.ives.webfluxpractice.exception.InputValidationException;
import com.ives.webfluxpractice.service.ReactiveMathService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("reactive-math")
public class ReactiveMathValidationController {

    @Autowired
    private ReactiveMathService reactiveMathService;

    @GetMapping("square/{input}/throw")
    public Mono<Response> findSquare(@PathVariable int input){
        if(input<10 || input>20)
            throw new InputValidationException(input);
        return reactiveMathService.findSquare(input);
    }

    @GetMapping("square/{input}/mono-error")
    public Mono<Response> momoError(@PathVariable int input){
        return Mono.just(input)
                .handle((val,sink) -> {
                    if(input>=10 && input<=20)
                        sink.next(val);
                    else
                        sink.error(new InputValidationException(input));
                })
                .cast(Integer.class)
                .flatMap(reactiveMathService::findSquare);
    }

}
