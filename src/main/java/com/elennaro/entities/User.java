package com.elennaro.entities;

import javax.persistence.*;
import java.util.Set;
import java.util.StringJoiner;

@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String username;
    private String password;

    @ManyToMany(fetch = FetchType.EAGER, cascade = {CascadeType.ALL})
    @JoinColumns({@JoinColumn(name = "user_id"), @JoinColumn(name = "role_id")})
    private Set<Role> roles;

    protected User() {}

    public User(String username) {
        this.username = username;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    @Override
    public String toString() {

        StringJoiner joiner = new StringJoiner(", ", "{ ", "}");
        for (Role role : roles)
            joiner.add(role.getRole());

        return String.format(
                "User[id=%d, username='%s', password='%s', roles='%s']",
                id, username, password, joiner.toString());
    }
}
