package com.elennaro.services;

import com.elennaro.entities.User;
import com.elennaro.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserDetails userLoaded;
        User client = userRepository.findByUsername(username);
        userLoaded = new org.springframework.security.core.userdetails.User(
                client.getUsername(),
                client.getPassword(),
                DummyAuthority.getAuth()
        );
        return userLoaded;
    }

    static private class DummyAuthority implements GrantedAuthority {
        static Collection<GrantedAuthority> getAuth() {
            List<GrantedAuthority> res = new ArrayList<>(1);
            res.add(new DummyAuthority());
            return res;
        }

        @Override
        public String getAuthority() {
            return "USER";
        }
    }
}
