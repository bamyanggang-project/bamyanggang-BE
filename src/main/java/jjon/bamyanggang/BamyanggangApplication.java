package jjon.bamyanggang;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude={SecurityAutoConfiguration.class})
public class BamyanggangApplication {

	public static void main(String[] args) {
		SpringApplication.run(BamyanggangApplication.class, args);
	}

}
	