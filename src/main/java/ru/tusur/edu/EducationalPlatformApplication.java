package ru.tusur.edu;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class EducationalPlatformApplication {

	public static void main(String[] args) {
		SpringApplication.run(EducationalPlatformApplication.class, args);
	}

}
