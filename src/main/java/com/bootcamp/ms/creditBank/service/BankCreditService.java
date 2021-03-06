package com.bootcamp.ms.creditBank.service;

import com.bootcamp.ms.commons.entity.BankCredit;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Optional;

public interface BankCreditService {

    Flux<BankCredit> findAll();
    Mono<BankCredit> findById(String id);
    Mono<BankCredit> save(BankCredit bankCredit);
    Mono<Void> delete(BankCredit bankCredit);
    Mono<Double> checkAvailableBalance(String idClient);
    Mono<Double> checkValidateDateExpiredCreditxClient(String idClient);

    Optional<BankCredit> findByIdOpt(String id);
}
