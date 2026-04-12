package dev.muriloamaral.resource;

import dev.muriloamaral.dto.CourseDTO;
import dev.muriloamaral.mapper.CourseMapper;
import dev.muriloamaral.model.Course;
import dev.muriloamaral.model.Lesson;
import dev.muriloamaral.service.CourseService;

import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.*;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import jakarta.annotation.security.RolesAllowed;

@Path("/courses")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class CourseResource {

    @Inject
    CourseService service;

    @Context
    UriInfo uriInfo;

    @POST
    @RolesAllowed("ADMIN")
    public Response create(@Valid Course course) {

        Course created = service.create(course);

        URI location = uriInfo.getAbsolutePathBuilder()
                .path(created.id.toString())
                .build();

        return Response.created(location)
                .entity(CourseMapper.toDTO(created))
                .build();
    }

    @GET
    public List<CourseDTO> listAll() {

        return service.listAll()
                .stream()
                .map(CourseMapper::toDTO)
                .collect(Collectors.toList());
    }

    @GET
    @Path("/{id}")
    public Response findById(@PathParam("id") Long id) {

        Course course = service.findById(id);

        if (course == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        return Response.ok(CourseMapper.toDTO(course)).build();
    }

    @PUT
    @RolesAllowed("ADMIN")
    @Path("/{id}")
    public Response update(@PathParam("id") Long id,
                           @Valid Course updated) {

        Course course = service.update(id, updated);

        if (course == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        return Response.ok(CourseMapper.toDTO(course)).build();
    }

    @DELETE
    @RolesAllowed("ADMIN")
    @Path("/{id}")
    public Response delete(@PathParam("id") Long id) {

        boolean deleted = service.delete(id);

        if (!deleted) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        return Response.noContent().build();
    }

    @POST
    @RolesAllowed("ADMIN")
    @Path("/{courseId}/lessons")
    public Response createLesson(@PathParam("courseId") Long courseId,
                                 @Valid Lesson lesson) {

        Lesson created = service.createLesson(courseId, lesson);

        if (created == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        return Response.status(Response.Status.CREATED).entity(created).build();
    }

    @GET
    @Path("/{courseId}/lessons")
    public Response listLessons(@PathParam("courseId") Long courseId) {

        Course course = service.findById(courseId);

        if (course == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        return Response.ok(course.lessons).build();
    }
}