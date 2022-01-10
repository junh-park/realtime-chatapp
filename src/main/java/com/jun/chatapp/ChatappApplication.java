package com.jun.chatapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories("com.jun.chatapp.repository")
public class ChatappApplication {

	public static void main(String[] args) {
		SpringApplication.run(ChatappApplication.class, args);
	}

}
