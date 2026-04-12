package dev.muriloamaral.service;

import dev.muriloamaral.model.User;
import dev.muriloamaral.repository.UserRepository;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

@ApplicationScoped
public class UserService {

    @Inject
    UserRepository repository;

    @Transactional
    public User create(User user) {

        if (repository.findByEmail(user.email) != null) {
            throw new RuntimeException("EMAIL_EXISTS");
        }

        user.role = "USER";

        repository.persist(user);

        return user;
    }

    public User findByEmail(String email) {
        return repository.findByEmail(email);
    }
}