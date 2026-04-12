package dev.muriloamaral.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    @NotBlank
    public String name;

    @NotBlank
    @Email
    @Column(unique = true)
    public String email;

    @NotBlank
    @Size(min = 8)
    public String password;

    public String role;
}
