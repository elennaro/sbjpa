package com.elennaro.main;

import com.elennaro.entities.User;
import com.elennaro.repositories.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.orm.jpa.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@ComponentScan(basePackages = "com.elennaro.config")
@SpringBootApplication
@EntityScan(basePackages = "com.elennaro.entities")
@EnableJpaRepositories(basePackages = {"com.elennaro.repositories"})
public class Application {
    private static final Logger log = LoggerFactory.getLogger(Application.class);

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    public CommandLineRunner demo(@SuppressWarnings("SpringJavaAutowiringInspection") UserRepository repository) {
        return (args) -> {
            User user = new User("admin");
            user.setPassword("1234");
            // save a user
            repository.save(user);

            // fetch all users
            log.info("Users found with findAll():");
            log.info("-------------------------------");
            for (User fetchedUser : repository.findAll()) {
                log.info(user.toString());
            }
            log.info("");
        };
    }
}
