package com.findjob.models;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "resume_pdf")
public class ResumePdf {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "resume_data_pdf")
    private byte[] resumeDataPdf;

    @OneToOne(mappedBy = "resumePdf")
    private Person person;
}
