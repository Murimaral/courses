package dev.muriloamaral.resources;

import dev.muriloamaral.models.Course;
import dev.muriloamaral.models.Lesson;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.*;

import java.net.URI;
import java.util.List;
import java.util.Map;

@Path("/courses")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class CourseResource {

    @Context
    UriInfo uriInfo;

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Transactional
    public Response create(@Valid Course course) {

        course.persist();

        URI location = uriInfo.getAbsolutePathBuilder()
                .path(course.id.toString())
                .build();

        return Response.created(location)
                .entity(Map.of("message", "Curso criado com sucesso"))
                .build();
    }

    @GET
    public Response listAll() {
        List<Course> courses = Course.listAll();

        return Response.ok(Map.of(
                "message", "Encontrado",
                "data", courses
        )).build();
    }

    @GET
    @Path("/{id}")
    public Response findById(@PathParam("id") Long id) {

        Course course = Course.findById(id);

        if (course == null) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity(Map.of("message", "Não encontrado"))
                    .build();
        }

        return Response.ok(Map.of(
                "message", "Encontrado",
                "data", course
        )).build();
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{id}")
    @Transactional
    public Response update(@PathParam("id") Long id, @Valid Course updated) {

        Course course = Course.findById(id);

        if (course == null) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity(Map.of("message", "Curso não existe"))
                    .build();
        }

        course.name = updated.name;

        return Response.ok(Map.of(
                "message", "Atualizado"
        )).build();
    }

    @DELETE
    @Path("/{id}")
    @Transactional
    public Response delete(@PathParam("id") Long id) {

        Course course = Course.findById(id);

        if (course == null) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity(Map.of("message", "Não existe"))
                    .build();
        }

        course.delete();

        return Response.noContent().build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{courseId}/lessons")
    @Transactional
    public Response createLesson(@PathParam("courseId") Long courseId,
                                 @Valid Lesson lesson) {

        Course course = Course.findById(courseId);

        if (course == null) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity(Map.of("message", "Curso não existe"))
                    .build();
        }

        lesson.course = course;
        lesson.persist();

        return Response.status(Response.Status.CREATED)
                .entity(Map.of("message", "Aula criada com sucesso"))
                .build();
    }

    @GET
    @Path("/{courseId}/lessons")
    public Response listLessons(@PathParam("courseId") Long courseId) {

        Course course = Course.findById(courseId);

        if (course == null) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity(Map.of("message", "Curso não existe"))
                    .build();
        }

        return Response.ok(course.lessons).build();
    }
}