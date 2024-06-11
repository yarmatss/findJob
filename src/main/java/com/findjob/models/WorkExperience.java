package com.findjob.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.time.LocalDate;

@Entity
@Data
@Table(name = "work_experience")
public class WorkExperience {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @NotEmpty(message = "Company name should not be empty")
    @Column(name = "company_name")
    @Size(min = 2, max = 255, message = "Company name should be between 2 and 255 characters")
    private String companyName;

    @Size(min = 2, max = 255, message = "Position should be between 2 and 255 characters")
    @NotEmpty(message = "Position should not be empty")
    @Column(name = "position")
    private String position;

    @Temporal(TemporalType.DATE)
    @Column(name = "start_date")
    private LocalDate startDate;

    @Temporal(TemporalType.DATE)
    @Column(name = "end_date")
    private LocalDate endDate;

    @ManyToOne
    @JoinColumn(name = "person_id")
    private Person person;
}
