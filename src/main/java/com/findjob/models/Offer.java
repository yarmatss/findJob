package com.findjob.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "offer")
public class Offer {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Size(min = 2, max = 255, message = "Title must be between 2 and 255 characters")
    @Column(name = "title")
    private String title;

    @Size(min = 2, max = 1000, message = "Description must be between 2 and 1000 characters")
    @Column(name = "description")
    private String description;

    @ManyToOne
    @JoinColumn(name = "offer_category_id")
    private OfferCategory offerCategory;

    @NotEmpty(message = "Offer type should not be empty")
    @Column(name = "offer_type")
    private String offerType;

    @Size(min = 2, max = 255, message = "Company name must be between 2 and 255 characters")
    @Column(name = "company_name")
    private String companyName;

    @NotEmpty(message = "Required skills should not be empty")
    @Column(name = "required_skills")
    private String requiredSkills;

    @Min(value = 0, message = "Minimum salary must be greater than or equal to 0")
    @Column(name = "min_salary")
    private int minSalary;

    @Min(value = 0, message = "Maximum salary must be greater than or equal to 0")
    @Column(name = "max_salary")
    private int maxSalary;

    @Column(name = "min_experience_years")
    @Min(value = 0, message = "Minimum experience years must be greater than or equal to 0")
    private int minExperienceYears;

    @Min(value = 0, message = "Maximum experience years must be greater than or equal to 0")
    @Column(name = "max_experience_years")
    private int maxExperienceYears;

    @NotEmpty(message = "Location should not be empty")
    @Size(min = 2, max = 255, message = "Location must be between 2 and 255 characters")
    @Column(name = "location")
    private String location;

    @Column(name = "company_logo")
    private byte[] companyLogo;

    @Column(name = "posted_at")
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime postedAt;

    @Column(name = "application_count")
    private int applicationCount;

    @ManyToOne
    @JoinColumn(name = "employer_id")
    private Person employer;
}
