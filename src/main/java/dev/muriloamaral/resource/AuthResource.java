package dev.muriloamaral.resource;

import dev.muriloamaral.model.User;
import dev.muriloamaral.service.UserService;

import io.smallrye.jwt.build.Jwt;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.*;

import java.time.Duration;

@Path("/auth")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class AuthResource {

    @Inject
    UserService service;

    public static class AuthRequest {
        public String email;
        public String password;
    }

    @POST
    @Path("/token")
    public Response login(AuthRequest request) {

        User user = service.findByEmail(request.email);

        if (user == null || !user.password.equals(request.password)) {
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }

        String token = Jwt.issuer("courses-api")
                .subject(user.email)
                .groups(user.role)
                .expiresIn(Duration.ofHours(1))
                .sign();

        return Response.ok(
                java.util.Map.of(
                        "token", token,
                        "expiresIn", 3600
                )
        ).build();
    }
}