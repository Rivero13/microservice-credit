package com.bootcamp.ms.creditBank.service.impl;

import com.bootcamp.ms.commons.entity.Client;
import com.bootcamp.ms.creditBank.ClientConfig;
import com.bootcamp.ms.creditBank.service.ClientService;
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
public class ClientServiceImpl implements ClientService {

    @Autowired
    private WebClient client;

    @Autowired
    private ClientConfig clientConfig;

    private final ReactiveCircuitBreakerFactory reactiveCircuitBreakerFactory;

    @Override
    public Flux<Client> getAll() {
        return client.get().uri(clientConfig.getUrl().concat("/all"))
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .flatMapMany(response -> response.bodyToFlux(Client.class))
                .transform(it -> {
                    ReactiveCircuitBreaker rcb = reactiveCircuitBreakerFactory.create("customer-service");
                    return rcb.run(it, throwable -> fallbackClient());
                });
    }

    @Override
    public Mono<Client> find(String id) {

        Map<String, Object> params = new HashMap<>();
        params.put("id", id);

        return client.get()
                .uri(clientConfig.getUrl().concat("/{id}"), params)
                .accept(MediaType.APPLICATION_JSON)
                .exchangeToMono(response -> response.bodyToMono(Client.class))
                .transform(it -> {
                    ReactiveCircuitBreaker rcb = reactiveCircuitBreakerFactory.create("customer-service");
                    return rcb.run(it, throwable -> fallbackMonoClient());
                });
    }

    private Flux<Client> fallbackClient() {
        // fetch results from the cache
        return Flux.just(new Client("","","","","",""));
    }

    private Mono<Client> fallbackMonoClient() {
        // fetch results from the cache
        return Mono.just(new Client("","","","","",""));
    }
}
