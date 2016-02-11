package com.elennaro.entities;

import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Set;
import java.util.StringJoiner;

@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @NotEmpty
    @NotNull
    @Size(min = 2, max = 30)
    private String username;

    @NotEmpty
    @NotNull
    @Size(min = 4, max = 100)
    private String password;

    @Transient
    private String confirmPassword;

    @ManyToMany(fetch = FetchType.EAGER, cascade = {CascadeType.ALL})
    @JoinColumns({@JoinColumn(name = "user_id"), @JoinColumn(name = "role_id")})
    private Set<Role> roles;

    protected User() {}

    public User(String username) {
        this.username = username;
    }

    public User(String password, String username) {
        this.password = password;
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

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    @Override
    public String toString() {

        StringJoiner joiner = new StringJoiner(", ", "{ ", " }");
        if (roles != null)
            for (Role role : roles)
                joiner.add(role.getRole());

        String ret = String.format(
                "User[id=%d, username='%s', password='%s', roles='%s']",
                id, username, password, joiner.toString());
        return ret;
    }
}
