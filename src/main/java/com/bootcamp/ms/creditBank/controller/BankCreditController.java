package com.bootcamp.ms.creditBank.controller;

import com.bootcamp.ms.commons.entity.BankAccount;
import com.bootcamp.ms.commons.entity.BankCredit;
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

import java.time.LocalDate;
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


    @GetMapping(value = "checkValidateDateExpiredCreditxClient/{id}")
    public Mono<Double> checkValidateDateExpiredCreditxClient(@PathVariable String id) {
        return bankCreditService.checkValidateDateExpiredCreditxClient(id);
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
                                bankCredit.setDate(LocalDate.now());
                                bankCredit.setDateExpired(bankCredit.getDate().plusMonths(1));
                                bankCredit.maxMovement(bankCredit.getType());
                                logger.info(c.getFirstName());
                                if(bankCredit.getType().equalsIgnoreCase("personal")){
                                    return bankCreditService.save(bankCredit);
                                }
                                return Mono.empty();
                            });
                });
    }

    @DeleteMapping(value = "/{id}")
    public Mono<Void> delete(@PathVariable String id) {
        return bankCreditService.findById(id).flatMap(p -> { return bankCreditService.delete(p); });
    }

    @PutMapping(value = "/transfer/{id}/{amount}/{idDestination}")
    public Mono<BankCredit> transfer(@PathVariable String id, @PathVariable double amount, @PathVariable String idDestination){

        return bankCreditService.findById(id)
                .flatMap(b -> {
                    Double currentAmount = b.getAmount();

                    if(currentAmount > amount){
                        currentAmount -= amount;
                        b.setAmount(currentAmount);

                        bankCreditService.findById(idDestination)
                                .flatMap(b1 -> {
                                    Double currentAmountDestination = b1.getAmount() + amount;
                                    b1.setAmount(currentAmountDestination);
                                    return bankCreditService.save(b1);
                                }).subscribe();
                    }

                    return bankCreditService.save(b);
                });
    }

}
