package dev.muriloamaral.model;

import io.quarkus.hibernate.orm.panache.PanacheEntity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Course extends PanacheEntity {

    @NotBlank
    @Size(min = 3)
    public String name;

    @OneToMany(mappedBy = "course", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    public List<Lesson> lessons = new ArrayList<>();
}