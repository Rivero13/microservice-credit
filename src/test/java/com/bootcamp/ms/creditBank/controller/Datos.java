package com.bootcamp.ms.creditBank.controller;

import com.bootcamp.ms.commons.entity.BankCredit;
import com.bootcamp.ms.commons.entity.Client;
import com.bootcamp.ms.commons.entity.ProductBank;
import reactor.core.publisher.Flux;
import java.time.LocalDate;
public class Datos {

    public final static ProductBank productBank = ProductBank.builder()
            .id("123")
            .description("Ahorro")
            .build();

    public final static ProductBank productBank2 = ProductBank.builder()
            .id("456")
            .description("Crédito")
            .build();

    public final static Client client = Client.builder()
            .id("258")
            .firstName("Raul")
            .lastName("Meza")
            .documentNumber("73994012")
            .age("27")
            .type("Personal")
            .build();

    public final static Client client2 = Client.builder()
            .id("147")
            .firstName("Victor")
            .lastName("Meza")
            .documentNumber("73994020")
            .age("25")
            .type("Empresarial")
            .build();

    public final static Flux<BankCredit> BANKCREDIT = Flux.just(
            new BankCredit("62dae2f624f70813e1068adf", "Ahorro", 5000, LocalDate.now(),LocalDate.now().plusMonths(1), 1200.00,0.0, "123", "258", client, productBank),
            new BankCredit("62dae2f624f90813e1068adf", "Crédito", 7000, LocalDate.now(),LocalDate.now().plusMonths(1), 1000.00,0.0, "456", "147", client2, productBank2)
    );
}
