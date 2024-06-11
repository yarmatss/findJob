package com.findjob.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.List;

@Entity
@Data
@Table(name = "offer_category")
public class OfferCategory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @NotEmpty(message = "Category should not be empty")
    @Size(min = 2, max = 255, message = "Category must be between 2 and 255 characters")
    @Column(name = "category")
    private String category;

    @NotEmpty(message = "Description should not be empty")
    @Size(min = 2, max = 1000, message = "Description must be between 2 and 1000 characters")
    @Column(name = "description")
    private String description;

    @OneToMany(mappedBy = "offerCategory", cascade = CascadeType.ALL)
    private List<Offer> offers;
}
