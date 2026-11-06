package br.com.spring_order_system;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

@Import(TestcontainersConfiguration.class)
@SpringBootTest
class SpringOrderSystemApplicationTests {

	@Test
	void contextLoads() {
	}

}
