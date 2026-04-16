package dev.muriloamaral.model;

import io.quarkus.security.jpa.Password;
import io.quarkus.security.jpa.Roles;
import io.quarkus.security.jpa.UserDefinition;
import io.quarkus.security.jpa.Username;
import jakarta.persistence.*;

@Entity
@Table(name = "users")
@UserDefinition
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    @Column(nullable = false)
    public String name;

    @Username
    @Column(nullable = false, unique = true)
    public String email;

    @Password
    @Column(nullable = false)
    public String password;

    @Roles
    @Column(nullable = false)
    public String role;
}