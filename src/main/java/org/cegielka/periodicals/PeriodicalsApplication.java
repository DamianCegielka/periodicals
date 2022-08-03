package org.cegielka.periodicals;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;


@EnableJpaRepositories
@SpringBootApplication(scanBasePackages={"org.cegielka.periodicals"})
public class PeriodicalsApplication {
	public static void main(String[] args) {
		SpringApplication.run(PeriodicalsApplication.class, args);
	}

}
