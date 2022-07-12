package com.bootcamp.ms.creditBank.controller;

import com.bootcamp.ms.commons.entity.BankCredit;
import com.bootcamp.ms.commons.entity.Client;
import com.bootcamp.ms.commons.entity.ProductBank;
import com.bootcamp.ms.creditBank.service.BankCreditService;
import com.bootcamp.ms.creditBank.service.ClientService;
import com.bootcamp.ms.creditBank.service.ProductBankService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping("/bankCredit")
public class BankCreditController {

    @Autowired
    private BankCreditService bankCreditService;

    @Autowired
    private ProductBankService productBankService;

    @Autowired
    private ClientService clientService;

    private final Logger logger = LoggerFactory.getLogger(BankCreditController.class);

    @GetMapping(value = "/all")
    public Flux<BankCredit> getAll() {
        return bankCreditService.findAll();
    }

    @GetMapping(value = "/{id}")
    public Mono<BankCredit> findById(@PathVariable String id) {
        return bankCreditService.findById(id);
    }

    @GetMapping(value = "getAvailableBalancesxcliente/{id}")
    public Mono<Double> getAvailableBalances(@PathVariable String id) {

        return bankCreditService.checkAvailableBalance(id);
    }

    @PostMapping(value = "/create")
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<BankCredit> chargeCreditCardConsumption(@RequestBody BankCredit bankCredit) {

        productBankService.find(bankCredit.getIdProduct())
                .flatMap(p -> {
                    logger.info(p.getDescription());
                    bankCredit.setProductBank(p);
                    return Mono.empty();
                }).subscribe();

        return clientService.find(bankCredit.getIdClient())
                .flatMap(c -> {
                    bankCredit.setClient(c);
                    bankCredit.maxMovement(bankCredit.getType());
                    logger.info(c.getFirstName());

                    if(c.getType().equalsIgnoreCase("personal")){
                        return bankCreditService.save(bankCredit);
                    }else{
                    }
                        return Mono.empty();
                });
    }

    @DeleteMapping(value = "/{id}")
    public Mono<Void> delete(@PathVariable String id) {
        return bankCreditService.findById(id).flatMap(p -> {
           return bankCreditService.delete(p);
        });
    }

}
