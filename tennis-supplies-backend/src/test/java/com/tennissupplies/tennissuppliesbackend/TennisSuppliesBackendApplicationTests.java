package com.tennissupplies.tennissuppliesbackend;
import com.tennissupplies.tennissuppliesbackend.models.StringEntity;
import com.tennissupplies.tennissuppliesbackend.repository.StringRepository;
import com.tennissupplies.tennissuppliesbackend.services.StringService;
import org.junit.Assert;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
@SpringBootTest
@ActiveProfiles("test")
public class TennisSuppliesBackendApplicationTests {
	@Autowired
	private StringService stringService;

	@Autowired
	private StringRepository stringRepository;
	@AfterEach
	public void cleanUp() {
		stringRepository.deleteAll();
	}
	@Test
	public void TestRetrieveString() {

		stringRepository.saveAll(List.of(new StringEntity("String 1"), new StringEntity("String 2"), new StringEntity("String 3")));

		List<StringEntity> strings = stringService.list();

		Assert.assertEquals(3, strings.size());
	}
	@Test
	public void TestAddString() {
		String newString = "Testing123";
		stringService.setString(newString);
		List<StringEntity> strings = stringService.list();
		Assert.assertEquals(1, strings.size());

	}

}
