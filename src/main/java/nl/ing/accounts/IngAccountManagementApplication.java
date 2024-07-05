package nl.ing.accounts;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableAutoConfiguration
public class IngAccountManagementApplication {

	public static void main(String[] args) {
		SpringApplication.run(IngAccountManagementApplication.class, args);
	}

}
