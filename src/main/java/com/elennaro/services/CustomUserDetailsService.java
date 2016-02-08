package com.elennaro.services;

import com.elennaro.entities.User;
import com.elennaro.repositories.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    UsersRepository usersRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserDetails userLoaded;
        User client = usersRepository.findByUsername(username);
        userLoaded = new org.springframework.security.core.userdetails.User(
                client.getUsername(),
                client.getPassword(),
                client.getRoles()
        );
        return userLoaded;
    }
}
