package com.findjob.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.time.LocalDate;

@Entity
@Data
@Table(name = "qualification")
public class Qualification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "degree")
    @NotEmpty(message = "Degree should not be empty")
    @Size(min = 2, max = 255, message = "Degree should be between 2 and 255 characters")
    private String degree;

    @Column(name = "university")
    @NotEmpty(message = "University should not be empty")
    @Size(min = 2, max = 255, message = "University should be between 2 and 255 characters")
    private String university;

    @Column(name = "start_date")
    @Temporal(TemporalType.DATE)
    private LocalDate startDate;

    @Column(name = "end_date")
    @Temporal(TemporalType.DATE)
    private LocalDate endDate;

    @ManyToOne
    @JoinColumn(name = "person_id")
    private Person person;
}
