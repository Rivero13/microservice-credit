package com.bootcamp.ms.creditBank.service;

import com.bootcamp.ms.commons.entity.Client;
import org.springframework.web.bind.annotation.PathVariable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ClientService {

    Flux<Client> getAll();

    Mono<Client> find(@PathVariable String id);
}
