package com.ives.webfluxpractice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.reactive.function.client.WebClient;

public class Lec01GetSingleResponseTest extends BaseTest{

    // 這邊需要建立一個專屬的Bean,故要使用Configuration
    @Autowired
    private WebClient webClient;



}
