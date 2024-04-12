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
	void testRetrieveString() {

		stringRepository.saveAll(List.of(new StringEntity("String 1"), new StringEntity("String 2"), new StringEntity("String 3")));

		List<StringEntity> strings = stringService.list();

		Assertions.assertEquals(3, strings.size());
	}
	@Test
	void testAddString() {
		String newString = "Testing123";
		stringService.setString(newString);
		List<StringEntity> strings = stringService.list();
		Assertions.assertEquals(1, strings.size());

	}

}
