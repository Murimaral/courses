package dev.muriloamaral.repository;

import dev.muriloamaral.model.User;
import io.quarkus.hibernate.orm.panache.PanacheRepository;

import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class UserRepository implements PanacheRepository<User> {

    public User findByEmail(String email) {
        return find("email", email).firstResult();
    }

    public User findByName(String name) {
        return find("name", name).firstResult();
    }
}