package dev.muriloamaral.models;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.JoinColumn;
import jakarta.validation.constraints.NotBlank;

@Entity
public class Lesson extends PanacheEntity {

    @NotBlank(message = "Nome da aula é obrigatório")
    public String name;

    @ManyToOne
    @JoinColumn(name = "course_id")
    public Course course;
}