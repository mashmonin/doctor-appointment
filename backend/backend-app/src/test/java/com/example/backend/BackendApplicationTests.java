package com.example.backend;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class BackendApplicationTests {

	@Disabled("Нужен запущенный PostgreSQL, пока не настроен Testcontainers")
	@Test
	void contextLoads() {
	}

}
