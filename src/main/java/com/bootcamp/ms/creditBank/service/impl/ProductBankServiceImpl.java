package com.bootcamp.ms.creditBank.service.impl;


import com.bootcamp.ms.commons.entity.ProductBank;
import com.bootcamp.ms.creditBank.ProductBankConfig;
import com.bootcamp.ms.creditBank.service.ProductBankService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.circuitbreaker.ReactiveCircuitBreaker;
import org.springframework.cloud.client.circuitbreaker.ReactiveCircuitBreakerFactory;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class ProductBankServiceImpl implements ProductBankService {

    @Autowired
    private WebClient client;

    @Autowired
    private ProductBankConfig productBankConfig;

    private final ReactiveCircuitBreakerFactory reactiveCircuitBreakerFactory;

    @Override
    public Flux<ProductBank> findAll() {
        return client.get()
                .uri(productBankConfig.getUrl().concat("/all"))
                .accept(MediaType.APPLICATION_JSON).exchange().flatMapMany(response -> response.bodyToFlux(ProductBank.class))
                .transform(it -> {
                    ReactiveCircuitBreaker rcb = reactiveCircuitBreakerFactory.create("customer-service");
                    return rcb.run(it, throwable -> fallbackProductBank());
                });
    }

    @Override
    public Mono<ProductBank> find(String id) {
        Map<String, Object> params = new HashMap<>();
        params.put("id", id);

        return client.get().uri(productBankConfig.getUrl().concat("/{id}"), params).accept(MediaType.APPLICATION_JSON).exchangeToMono(response -> response.bodyToMono(ProductBank.class))
                .transform(it -> {
                    ReactiveCircuitBreaker rcb = reactiveCircuitBreakerFactory.create("customer-service");
                    return rcb.run(it, throwable -> fallbackMonoProductBank());
                });
    }

    private Flux<ProductBank> fallbackProductBank() {
        // fetch results from the cache
        return Flux.just(new ProductBank("0","Error product bank"));
    }

    private Mono<ProductBank> fallbackMonoProductBank() {
        // fetch results from the cache
        return Mono.just(new ProductBank("0","Error product bank"));
    }
}
