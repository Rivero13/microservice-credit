package com.bootcamp.ms.creditBank.controller;

import com.bootcamp.ms.commons.entity.BankCredit;
import com.bootcamp.ms.creditBank.repository.BankCreditRepository;
import com.bootcamp.ms.creditBank.service.BankCreditService;
import com.bootcamp.ms.creditBank.service.impl.BankCreditServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;

class BankCreditControllerTest {

    BankCreditRepository bankCreditRepository;
    BankCreditService bankCreditService;

    @BeforeEach
    void setUp() {
        bankCreditRepository = Mockito.mock(BankCreditRepository.class);
        bankCreditService = new BankCreditServiceImpl(bankCreditRepository);
    }

    @Test
    void findByIdOpt() {
        Mockito.when(bankCreditRepository.findAll()).thenReturn(Datos.BANKCREDIT);
        Optional<BankCredit> bankAccount = bankCreditService.findByIdOpt("62dae2f624f70813e1068adf");

        assertAll(
                () -> assertTrue(bankAccount.isPresent()),
                () -> assertTrue(bankAccount.orElseThrow().getAmount() > 1500),
                () -> assertEquals("Ahorro", bankAccount.orElseThrow().getType()),
                () -> assertEquals("Crédito", bankAccount.orElseThrow().getProductBank().getDescription()),
                () -> assertEquals("Raul", bankAccount.orElseThrow().getClient().getFirstName()),
                () -> assertEquals("Meza", bankAccount.orElseThrow().getClient().getLastName())
        );
    }
}