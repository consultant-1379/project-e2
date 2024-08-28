package com.cloudmaturity.cloud;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class CloudApplicationTests {

	@Test
	void contextLoads() {
		assertTrue(true);
	}

	@Test
	void main() {
		CloudApplication.main(new String[] {});
		assertTrue(true);
	}

}
