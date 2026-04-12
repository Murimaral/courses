package dev.muriloamaral.mapper;

import dev.muriloamaral.dto.UserResponseDTO;
import dev.muriloamaral.model.User;

public final class UserMapper {

    private UserMapper() {
    }

    public static UserResponseDTO toDTO(User user) {
        UserResponseDTO dto = new UserResponseDTO();
        dto.id = user.id;
        dto.name = user.name;
        dto.email = user.email;
        dto.role = user.role;
        return dto;
    }
}
