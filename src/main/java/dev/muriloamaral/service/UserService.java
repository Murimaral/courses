package dev.muriloamaral.service;

import dev.muriloamaral.dto.UserCreateDTO;
import dev.muriloamaral.model.User;
import dev.muriloamaral.repository.UserRepository;
import io.quarkus.elytron.security.common.BcryptUtil;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

@ApplicationScoped
public class UserService {

    @Inject
    UserRepository repository;

    @Transactional
    public User create(UserCreateDTO dto) {
        if (repository.findByEmail(dto.email) != null) {
            throw new IllegalStateException("EMAIL_EXISTS");
        }

        User user = new User();
        user.name = dto.name;
        user.email = dto.email;
        user.password = BcryptUtil.bcryptHash(dto.password);
        user.role = "USER";

        repository.persist(user);
        return user;
    }

    public User findByEmail(String email) {
        return repository.findByEmail(email);
    }

    public User authenticate(String email, String plainPassword) {
        User user = repository.findByEmail(email);
        if (user == null) {
            return null;
        }

        if (!BcryptUtil.matches(plainPassword, user.password)) {
            return null;
        }

        return user;
    }

    @Transactional
    public void createAdminIfMissing() {
        if (repository.findByEmail("admin") != null) {
            return;
        }

        User admin = new User();
        admin.name = "admin";
        admin.email = "admin";
        admin.password = BcryptUtil.bcryptHash("admin");
        admin.role = "ADMIN";

        repository.persist(admin);
    }
}