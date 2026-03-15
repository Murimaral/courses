package dev.muriloamaral.mapper;

import dev.muriloamaral.dto.CourseDTO;
import dev.muriloamaral.dto.LessonDTO;
import dev.muriloamaral.model.Course;
import dev.muriloamaral.model.Lesson;

import java.util.stream.Collectors;

public class CourseMapper {

    public static CourseDTO toDTO(Course course) {

        CourseDTO dto = new CourseDTO();

        dto.id = course.id;
        dto.name = course.name;

        dto.lessons = course.lessons
                .stream()
                .map(CourseMapper::lessonToDTO)
                .collect(Collectors.toList());

        return dto;
    }

    public static LessonDTO lessonToDTO(Lesson lesson) {

        LessonDTO dto = new LessonDTO();

        dto.id = lesson.id;
        dto.name = lesson.name;

        return dto;
    }
}