package com.findjob.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@Table(name = "person")
public class Person {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotEmpty(message = "Username should not be empty")
    @Size(min = 2, max = 100, message = "Username should be between 2 and 100 characters")
    @Column(name = "username")
    private String username;

    @Column(name = "password")
    private String password;

    @NotEmpty(message = "First name should not be empty")
    @Size(min = 2, max = 40, message = "First name should be between 2 and 40 characters")
    @Column(name = "first_name")
    private String firstName;

    @NotEmpty(message = "Last name should not be empty")
    @Size(min = 2, max = 40, message = "Last name should be between 2 and 40 characters")
    @Column(name = "last_name")
    private String lastName;

    @NotEmpty(message = "Email should not be empty")
    @Email
    @Column(name = "email")
    private String email;

    @NotEmpty(message = "Phone number should not be empty")
    @Column(name = "phone")
    private String phone;

    @NotEmpty(message = "Location should not be empty")
    @Column(name = "location")
    private String location;

    @Column(name = "created_at")
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime createdAt;

    @NotEmpty(message = "Role should not be empty")
    @Column(name = "role")
    private String role;

    @Column(name = "about_me")
    @Size(min = 2, max = 1000, message = "Bio should be between 2 and 1000 characters")
    private String aboutMe;

    @OneToMany(mappedBy = "person", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Qualification> qualifications;

    @OneToMany(mappedBy = "person", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Skill> skills;

    @OneToMany(mappedBy = "person", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<WorkExperience> workExperiences;

    @OneToMany(mappedBy = "person", fetch = FetchType.EAGER)
    private List<Application> applications;

    @OneToMany(mappedBy = "employer", cascade = CascadeType.ALL)
    private List<Offer> offers;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "picture_id", referencedColumnName = "id")
    private Picture picture;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "resume_pdf_id", referencedColumnName = "id")
    private ResumePdf resumePdf;
}
