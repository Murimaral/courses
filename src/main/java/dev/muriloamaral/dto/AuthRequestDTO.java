package dev.muriloamaral.dto;

import jakarta.validation.constraints.NotBlank;

public class AuthRequestDTO {

    @NotBlank
    public String email;

    @NotBlank
    public String password;
}