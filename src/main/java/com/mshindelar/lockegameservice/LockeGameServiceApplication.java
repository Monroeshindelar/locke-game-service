package com.mshindelar.lockegameservice;

import com.mshindelar.lockegameservice.pokeapi.PokeApiClient;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class LockeGameServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(LockeGameServiceApplication.class, args);
	}
}
