package com.findjob.models;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "profile_picture")
public class ProfilePicture {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "profile_data")
    private byte[] pictureData;

    @Column(name = "profile_picture_content_type")
    private String contentType;

    @OneToOne(mappedBy = "profilePicture")
    private Person person;
}
