package dev.muriloamaral.config;

import dev.muriloamaral.service.UserService;
import io.quarkus.runtime.Startup;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

@Startup
@ApplicationScoped
public class AdminStartup {

    @Inject
    UserService userService;

    @PostConstruct
    @Transactional
    public void init() {
        userService.createAdminIfMissing();
    }
}