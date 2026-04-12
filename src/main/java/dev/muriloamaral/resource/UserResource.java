package dev.muriloamaral.resource;

import dev.muriloamaral.model.User;
import dev.muriloamaral.service.UserService;

import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.*;

import org.eclipse.microprofile.jwt.JsonWebToken;
import jakarta.annotation.security.RolesAllowed;

@Path("/users")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class UserResource {

    @Inject
    UserService service;

    @POST
    public Response create(@Valid User user) {

        try {
            User created = service.create(user);
            return Response.status(Response.Status.CREATED).entity(created).build();

        } catch (RuntimeException e) {
            if ("EMAIL_EXISTS".equals(e.getMessage())) {
                return Response.status(Response.Status.CONFLICT).build();
            }
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }
}

@Inject
JsonWebToken jwt;

@GET
@Path("/me")
@RolesAllowed({"USER","ADMIN"})
public Response me() {

    String email = jwt.getSubject();

    return Response.ok(
            java.util.Map.of("email", email)
    ).build();
}