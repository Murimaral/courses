package dev.muriloamaral.service;

import dev.muriloamaral.model.Course;
import dev.muriloamaral.model.Lesson;
import dev.muriloamaral.repository.CourseRepository;
import dev.muriloamaral.repository.LessonRepository;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

import java.util.List;

@ApplicationScoped
public class CourseService {

    @Inject
    CourseRepository courseRepository;

    @Inject
    LessonRepository lessonRepository;

    public List<Course> listAll() {
        return courseRepository.listAll();
    }

    public Course findById(Long id) {
        return courseRepository.findById(id);
    }

    @Transactional
    public Course create(Course course) {
        courseRepository.persist(course);
        return course;
    }

    @Transactional
    public Course update(Long id, Course updated) {

        Course course = courseRepository.findById(id);

        if (course == null) {
            return null;
        }

        course.name = updated.name;

        courseRepository.persist(course);

        return course;
    }

    @Transactional
    public boolean delete(Long id) {

        Course course = courseRepository.findById(id);

        if (course == null) {
            return false;
        }

        courseRepository.delete(course);
        return true;
    }

    @Transactional
    public Lesson createLesson(Long courseId, Lesson lesson) {

        Course course = courseRepository.findById(courseId);

        if (course == null) {
            return null;
        }

        lesson.course = course;

        lessonRepository.persist(lesson);

        return lesson;
    }
}