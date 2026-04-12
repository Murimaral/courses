package dev.muriloamaral.resource;

import dev.muriloamaral.dto.AuthRequestDTO;
import dev.muriloamaral.dto.AuthResponseDTO;
import dev.muriloamaral.model.User;
import dev.muriloamaral.service.UserService;
import io.smallrye.jwt.build.Jwt;

import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import java.time.Duration;
import java.util.Set;

@Path("/auth")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class AuthResource {

    @Inject
    UserService userService;

    @ConfigProperty(name = "jwt.expires-in")
    Long expiresIn;

    @POST
    @Path("/token")
    public Response token(AuthRequestDTO request) {
        if (request == null || request.email == null || request.password == null) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }

        User user = userService.findByEmail(request.email);

        if (user == null || !user.password.equals(request.password)) {
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }

        String jwt = Jwt.issuer("courses-api")
                .subject(user.email)
                .groups(Set.of(user.role))
                .expiresIn(Duration.ofSeconds(expiresIn))
                .sign();

        return Response.ok(new AuthResponseDTO(jwt, expiresIn)).build();
    }
}