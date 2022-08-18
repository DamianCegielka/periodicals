package org.cegielka.periodicals;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaRepositories
@SpringBootApplication
public class PeriodicalsApplication {
    public static void main(String[] args) {
        SpringApplication.run(PeriodicalsApplication.class, args);
    }

}
