package com.bootcamp.ms.creditBank.repository;

import com.bootcamp.ms.commons.entity.BankCredit;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface BankCreditRepository extends ReactiveMongoRepository<BankCredit, String> {
}
