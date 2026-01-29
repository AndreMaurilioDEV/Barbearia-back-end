package com.projeto.barbearia;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BarbeariaApplication {

	public static void main(String[] args) {


		Dotenv dotenv = Dotenv.load();
		System.setProperty("SPRING_USERNAME", dotenv.get("SPRING_USERNAME"));
		System.setProperty("SPRING_PASSWORD", dotenv.get("SPRING_PASSWORD"));
		System.setProperty("MAIL_USERNAME", dotenv.get("MAIL_USERNAME"));
		System.setProperty("MAIL_PASSWORD", dotenv.get("MAIL_PASSWORD"));
		System.setProperty("ADMIN_NAME", dotenv.get("ADMIN_NAME"));
		System.setProperty("ADMIN_EMAIL", dotenv.get("ADMIN_EMAIL"));
		System.setProperty("ADMIN_PASSWORD", dotenv.get("ADMIN_PASSWORD"));
		System.setProperty("JWT_SECRET", dotenv.get("JWT_SECRET"));
		SpringApplication.run(BarbeariaApplication.class, args);
	}

}
