package dev.muriloamaral.config;

import dev.muriloamaral.model.User;
import dev.muriloamaral.repository.UserRepository;

import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

@ApplicationScoped
public class AdminInitializer {

    @Inject
    UserRepository repository;

    @PostConstruct
    @Transactional
    public void init() {
        if (repository.findByEmail("admin") == null && repository.findByName("admin") == null) {
            User admin = new User();
            admin.name = "admin";
            admin.email = "admin";
            admin.password = "admin";
            admin.role = "ADMIN";

            repository.persist(admin);
        }
    }
}