package com.bootcamp.ms.creditBank.service;

import com.bootcamp.ms.commons.entity.BankCredit;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface BankCreditService {

    public Flux<BankCredit> findAll();
    public Mono<BankCredit> findById(String id);
    public Mono<BankCredit> save(BankCredit bankCredit);
    public Mono<Void> delete(BankCredit bankCredit);

    public Mono<BankCredit> findByIdClient(String id);

    public Mono<Double> checkAvailableBalance(String idClient);
}
