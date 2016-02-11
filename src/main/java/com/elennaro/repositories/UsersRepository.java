package com.elennaro.repositories;

import com.elennaro.entities.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsersRepository extends CrudRepository<User, Long>, UsersRepositoryCustom {

    User findByUsername(String lastName);

}
