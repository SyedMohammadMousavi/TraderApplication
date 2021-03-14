package com.traders.application.items;

import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootTest(classes = {RestTemplate.class, ModelMapper.class})
class ItemsApplicationTest {

    @Test
    public void contextLoads() {
    }

    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
        return builder.build();
    }

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

}