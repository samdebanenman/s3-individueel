package com.tennissupplies.tennissuppliesbackend;
import com.tennissupplies.tennissuppliesbackend.models.StringEntity;
import com.tennissupplies.tennissuppliesbackend.repository.StringRepository;
import com.tennissupplies.tennissuppliesbackend.services.StringService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.concurrent.ExecutionException;

@SpringBootTest
@ActiveProfiles("test")
class TennisSuppliesBackendApplicationTests {
	@Autowired
	private StringService stringService;

	@Autowired
	private StringRepository stringRepository;
	@AfterEach
	public void cleanUp() {
		stringRepository.deleteAll();
	}
	@Test
	void testRetrieveString() throws ExecutionException, InterruptedException {
		stringRepository.saveAll(List.of(new StringEntity("String 1"), new StringEntity("String 2"), new StringEntity("String 3")));

		// Wait for the asynchronous list method to complete
		List<StringEntity> strings = stringService.list().get();

		Assertions.assertEquals(3, strings.size());
	}

	@Test
	void testAddString() throws ExecutionException, InterruptedException {
		String newString = "Testing123";

		// Wait for the asynchronous setString method to complete
		stringService.setString(newString).get();

		// Wait for the asynchronous list method to complete
		List<StringEntity> strings = stringService.list().get();

		Assertions.assertEquals(1, strings.size());
	}
}
