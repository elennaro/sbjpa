package com.elennaro.repositories;

import com.elennaro.entities.User;
import org.springframework.data.repository.CrudRepository;

public interface UsersRepository extends CrudRepository<User, Long> {
    User findByUsername(String lastName);
}
