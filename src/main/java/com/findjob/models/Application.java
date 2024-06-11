package com.findjob.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.time.LocalDate;

@Entity
@Data
@Table(name = "application")
public class Application {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @ManyToOne
    @JoinColumn(name = "person_id")
    private Person person;

    @ManyToOne
    @JoinColumn(name = "offer_id")
    private Offer offer;

    @Column(name = "application_date")
    @Temporal(TemporalType.DATE)
    private LocalDate applicationDate;

    @Column(name = "status")
    @NotEmpty(message = "Status should not be empty")
    @Size(min = 2, max = 255, message = "Status should be between 2 and 255 characters")
    private String status;
}
