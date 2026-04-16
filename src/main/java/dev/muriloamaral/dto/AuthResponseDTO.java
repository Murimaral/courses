package dev.muriloamaral.dto;

public class AuthResponseDTO {

    public String token;
    public long expiresIn;

    public AuthResponseDTO() {
    }

    public AuthResponseDTO(String token, long expiresIn) {
        this.token = token;
        this.expiresIn = expiresIn;
    }
}