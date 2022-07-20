package com.bootcamp.ms.creditBank.service;

import com.bootcamp.ms.commons.entity.BankCredit;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface BankCreditService {

    Flux<BankCredit> findAll();
    Mono<BankCredit> findById(String id);
    Mono<BankCredit> save(BankCredit bankCredit);
    Mono<Void> delete(BankCredit bankCredit);

    Mono<BankCredit> findByIdClient(String id);

    Mono<Double> checkAvailableBalance(String idClient);
}
