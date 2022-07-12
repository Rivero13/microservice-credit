package com.bootcamp.ms.creditBank;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
@EntityScan({"com.bootcamp.ms.commons.entity"})
public class MicroserviceCreditBankApplication {

	public static void main(String[] args) {
		SpringApplication.run(MicroserviceCreditBankApplication.class, args);
	}

}
