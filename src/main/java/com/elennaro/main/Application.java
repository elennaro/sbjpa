package com.elennaro.main;

import com.elennaro.entities.Role;
import com.elennaro.entities.User;
import com.elennaro.repositories.UsersRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.orm.jpa.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import java.util.HashSet;

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
    public CommandLineRunner demo(
            @SuppressWarnings("SpringJavaAutowiringInspection") UsersRepository userRepo) {
        return (args) -> {
            Role roleAdmin = new Role("ROLE_ADMIN");
            Role roleUser = new Role("ROLE_USER");
            User admin = new User("admin");
            User user = new User("user");
            admin.setPassword("1234");
            user.setPassword("1234");
            admin.setRoles(new HashSet<Role>() {{add(roleAdmin);}});
            user.setRoles(new HashSet<Role>() {{add(roleUser);}});
            // save a user
            userRepo.registerUser(admin);
            userRepo.registerUser(user);

            // fetch all users
            log.info("Users found with findAll():");
            log.info("-------------------------------");
            for (User fetchedUser : userRepo.findAll()) {
                log.info(fetchedUser.toString());
            }
            log.info("");
        };
    }
}
