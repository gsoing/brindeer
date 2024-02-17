package org.gso.brinder.match;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;


@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class })
public class BrinderMatchApplication {

	public static void main(String[] args) {
		SpringApplication.run(BrinderMatchApplication.class, args);
	}

}
