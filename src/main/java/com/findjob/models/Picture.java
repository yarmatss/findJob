package com.findjob.models;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Data
@Table(name = "picture")
public class Picture {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "data")
    private byte[] pictureData;

    @Column(name = "picture_content_type")
    private String contentType;

    @OneToOne(mappedBy = "picture")
    private Person person;

    @OneToMany(mappedBy = "companyLogo")
    private List<Offer> offers;
}
