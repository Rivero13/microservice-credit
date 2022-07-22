package com.bootcamp.ms.creditBank;

import com.bootcamp.ms.creditBank.config.CircuitBreakerConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@EnableEurekaClient
@EntityScan({"com.bootcamp.ms.commons.entity"})
@Import({CircuitBreakerConfiguration.class})
public class MicroserviceCreditBankApplication {

	public static void main(String[] args) {SpringApplication.run(MicroserviceCreditBankApplication.class, args);}

}
