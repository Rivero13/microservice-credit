package com.bootcamp.ms.creditBank.service.impl;

import com.bootcamp.ms.commons.entity.BankCredit;
import com.bootcamp.ms.creditBank.repository.BankCreditRepository;
import com.bootcamp.ms.creditBank.service.BankCreditService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class BankCreditServiceImpl implements BankCreditService {

    @Autowired
    private BankCreditRepository bankCreditRepository;

    @Override
    public Flux<BankCredit> findAll() {
        return bankCreditRepository.findAll();
    }

    @Override
    public Mono<BankCredit> findById(String id) {
        return bankCreditRepository.findById(id);
    }

    @Override
    public Mono<BankCredit> save(BankCredit bankCredit) {
        return bankCreditRepository.save(bankCredit);
    }

    @Override
    public Mono<Void> delete(BankCredit bankCredit) {
        return bankCreditRepository.delete(bankCredit);
    }

    @Override
    public Mono<BankCredit> findByIdClient(String id) {
        Mono<BankCredit> bankCreditMono = bankCreditRepository.findAll()
                .filter(x -> x.getIdClient().equals(id)).single();

        return bankCreditMono;
    }

    @Override
    public Mono<Double> checkAvailableBalance(String idClient) {
        return  bankCreditRepository.findAll()
                .filter(x -> x.getIdClient().equals(idClient)).map(e -> {
                    e.setAvailableBalances(e.getMaxMovement() - e.getAmount());
                    return e.getAvailableBalances();
                }).single().switchIfEmpty(Mono.just(00.0));
    }
}
