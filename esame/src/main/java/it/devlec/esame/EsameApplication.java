package it.devlec.esame;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class EsameApplication {
	private static final Logger logger = LoggerFactory.getLogger(EsameApplication.class);
	public static void main(String[] args) {
		SpringApplication.run(EsameApplication.class, args);
	}


}
