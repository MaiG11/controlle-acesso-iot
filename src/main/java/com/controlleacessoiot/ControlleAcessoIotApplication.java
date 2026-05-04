package com.controlleacessoiot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ControlleAcessoIotApplication {

	public static void main(String[] args) {
		SpringApplication.run(ControlleAcessoIotApplication.class, args);
		System.out.println(" http://localhost:8080/api");
	}

}
