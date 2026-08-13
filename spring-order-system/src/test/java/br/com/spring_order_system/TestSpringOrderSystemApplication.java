package br.com.spring_order_system;

import org.springframework.boot.SpringApplication;

public class TestSpringOrderSystemApplication {

	public static void main(String[] args) {
		SpringApplication.from(SpringOrderSystemApplication::main).with(TestcontainersConfiguration.class).run(args);
	}

}
