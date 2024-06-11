package com.findjob.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Entity
@Data
@Table(name = "skill")
public class Skill {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "skill_name")
    @NotEmpty(message = "Skill should not be empty")
    @Size(min = 2, max = 255, message = "Skill should be between 2 and 255 characters")
    private String skillName;

    @Column(name = "experience")
    private int experience;

    @ManyToOne
    @JoinColumn(name = "person_id")
    private Person person;
}
