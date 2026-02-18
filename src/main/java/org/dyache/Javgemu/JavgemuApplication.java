package org.dyache.Javgemu;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class JavgemuApplication {

	public static void main(String[] args) {
		SpringApplication.run(JavgemuApplication.class, args);
	}

}
