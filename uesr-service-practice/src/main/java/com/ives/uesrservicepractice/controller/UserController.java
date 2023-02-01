package com.ives.uesrservicepractice.controller;

import com.ives.uesrservicepractice.dto.UserDto;
import com.ives.uesrservicepractice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("user")
public class UserController {

    @Autowired
    UserService service;

    @GetMapping("all")
    public Flux<UserDto> all(){
        return service.all();
    }

    @GetMapping("{id}")
    public Mono<ResponseEntity<UserDto>> getUserById(@PathVariable int id){
        return service.getUserById(id)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Mono<UserDto> createUser(@RequestBody Mono<UserDto> userDtoMono){
        return service.createUser(userDtoMono);
    }

    @PutMapping("{id}")
    public Mono<ResponseEntity<UserDto>> updateUser(@PathVariable int id, @RequestBody Mono<UserDto> userDtoMono){
        return service.updateUser(id,userDtoMono)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @DeleteMapping("{id}")
    public Mono<Void> deleteUser(@PathVariable int id){
        return service.deleteUser(id);
    }
}
