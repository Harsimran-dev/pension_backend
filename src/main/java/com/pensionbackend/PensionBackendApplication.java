package com.pensionbackend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class PensionBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(PensionBackendApplication.class, args);
	}

}
