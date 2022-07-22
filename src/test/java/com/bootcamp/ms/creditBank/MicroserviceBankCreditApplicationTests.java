package com.bootcamp.ms.creditBank;

import com.bootcamp.ms.commons.entity.BankCredit;
import com.bootcamp.ms.commons.entity.Client;
import com.bootcamp.ms.commons.entity.ProductBank;
import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import static org.junit.jupiter.api.Assertions.*;

class MicroserviceBankCreditApplicationTests {

	private final ProductBank productBank = ProductBank.builder()
			.id("62d6e6eea9d79e4479b49a3c")
			.description("Ahorro")
			.build();

	private final Client client = Client.builder()
			.id("62d6e6e4f9d79e4475849a3c")
			.firstName("Raul")
			.lastName("Meza")
			.age("27")
			.documentNumber("73994012")
			.type("Personal")
			.build();

	private final BankCredit bankCredit = BankCredit.builder()
			.id("62dae2f624f70813e1048adf")
			.type("Personal")
			.maxMovement(5000)
			.date(LocalDate.now())
			.dateExpired(LocalDate.now().plusMonths(1))
			.amount(0.00)
			.idProduct("62d6e6eea9d79e4479b49a3c")
			.idClient("62d6e6e4f9d79e4475849a3c")
			.productBank(productBank)
			.client(client)
			.build();

	@Test
	void create() {
		assertAll(
				() -> assertNotNull(bankCredit)
		);
	}

	@Test
	void testNotNull() {
		assertAll(
				() -> assertNotNull(bankCredit.getType()),
				() -> assertNotNull(bankCredit.getMaxMovement()),
				() -> assertNotNull(bankCredit.getDate()),
				() -> assertNotNull(bankCredit.getDateExpired()),
				() -> assertNotNull(bankCredit.getAmount()),
				() -> assertNotNull(bankCredit.getIdProduct()),
				() -> assertNotNull(bankCredit.getIdClient()),
				() -> assertNotNull(bankCredit.getProductBank()),
				() -> assertNotNull(bankCredit.getClient())
		);
	}

	@Test
	void findById() {
		assertAll(
				() -> assertNotNull(bankCredit.getId()),
				() -> assertEquals("62dae2f624f70813e1048adf", bankCredit.getId())
		);
	}

	@Test
	void findByClient() {
		assertAll(
				() -> assertNotNull(bankCredit.getIdClient()),
				() -> assertNotNull(bankCredit.getClient()),
				() -> assertEquals("62d6e6e4f9d79e4475849a3c", bankCredit.getClient().getId())
		);
	}

	@Test
	void findByProductBank() {
		assertAll(
				() -> assertNotNull(bankCredit.getIdProduct()),
				() -> assertNotNull(bankCredit.getProductBank()),
				() -> assertEquals("62d6e6eea9d79e4479b49a3c", bankCredit.getProductBank().getId())
		);
	}

}
