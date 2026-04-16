package dev.muriloamaral.resource;

import dev.muriloamaral.dto.UserCreateDTO;
import dev.muriloamaral.model.User;
import dev.muriloamaral.service.UserService;
import org.eclipse.microprofile.jwt.JsonWebToken;

import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/users")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class UserResource {

    @Inject
    UserService service;

    @Inject
    JsonWebToken jwt;

    @POST
    public Response create(@Valid UserCreateDTO userDto) {
        try {
            User created = service.create(userDto);
            return Response.status(Response.Status.CREATED)
                    .entity(java.util.Map.of(
                            "id", created.id,
                            "name", created.name,
                            "email", created.email,
                            "role", created.role
                    ))
                    .build();
        } catch (IllegalStateException e) {
            if ("EMAIL_EXISTS".equals(e.getMessage())) {
                return Response.status(Response.Status.CONFLICT).build();
            }
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }

    @GET
    @Path("/me")
    @RolesAllowed({"USER", "ADMIN"})
    public Response me() {
        User user = service.findByEmail(jwt.getSubject());

        if (user == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        return Response.ok(java.util.Map.of(
                "id", user.id,
                "name", user.name,
                "email", user.email,
                "role", user.role
        )).build();
    }
}