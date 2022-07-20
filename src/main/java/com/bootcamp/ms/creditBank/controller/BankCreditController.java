package com.bootcamp.ms.creditBank.controller;

import com.bootcamp.ms.commons.entity.BankCredit;
import com.bootcamp.ms.creditBank.service.BankCreditService;
import com.bootcamp.ms.creditBank.service.ClientService;
import com.bootcamp.ms.creditBank.service.ProductBankService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.DeleteMapping;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import java.util.Date;

@RestController
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

        return productBankService.find(bankCredit.getIdProduct())
                .flatMap(p -> {
                    logger.info(p.getDescription());
                    bankCredit.setProductBank(p);
                    return clientService.find(bankCredit.getIdClient())
                            .flatMap(c -> {
                                bankCredit.setClient(c);
                                bankCredit.setDate(new Date());
                                bankCredit.maxMovement(bankCredit.getType());
                                logger.info(c.getFirstName());
                                if(bankCredit.getType().equalsIgnoreCase("personal")){
                                    return bankCreditService.save(bankCredit);
                                }
                                return Mono.empty();
                            });
                });

       // return Mono.just(bankCredit);
    }

    @DeleteMapping(value = "/{id}")
    public Mono<Void> delete(@PathVariable String id) {
        return bankCreditService.findById(id).flatMap(p -> {
           return bankCreditService.delete(p);
        });
    }

}
