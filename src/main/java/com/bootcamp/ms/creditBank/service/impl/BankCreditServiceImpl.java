package com.bootcamp.ms.creditBank.service.impl;

import com.bootcamp.ms.commons.entity.BankCredit;
import com.bootcamp.ms.creditBank.controller.BankCreditController;
import com.bootcamp.ms.creditBank.repository.BankCreditRepository;
import com.bootcamp.ms.creditBank.service.BankCreditService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDate;
import java.time.ZoneOffset;

@Service
public class BankCreditServiceImpl implements BankCreditService {

    private final Logger log = LoggerFactory.getLogger(BankCreditServiceImpl.class);

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
    public Mono<Double> checkAvailableBalance(String idClient) {
        return bankCreditRepository.findAll()
                .filter(x -> x.getIdClient().equals(idClient)).map(e -> {
                    e.setAvailableBalances(e.getMaxMovement() - e.getAmount());
                    return e.getAvailableBalances();
                }).single().switchIfEmpty(Mono.just(00.0));
    }

    @Override
    public Mono<Double> checkValidateDateExpiredCreditxClient(String idClient) {
        return bankCreditRepository.findAll()
                .filter(x -> x.getIdClient().equals(idClient)).map(b -> {
                    log.info("tempBankCredit {}", b.toString());
                    Double currentAmount = b.getAmount();
                    if(currentAmount != null && currentAmount > 0 && (LocalDate.now().atStartOfDay(ZoneOffset.UTC).toInstant().toEpochMilli() > b.getDateExpired().atStartOfDay(ZoneOffset.UTC).toInstant().toEpochMilli())){
                        return currentAmount;
                    }
                    return 0.0;
                }).single();
    }
}
