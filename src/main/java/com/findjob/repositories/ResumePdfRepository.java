package com.findjob.repositories;

import com.findjob.models.ResumePdf;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ResumePdfRepository extends JpaRepository<ResumePdf, Integer> {
    Optional<ResumePdf> findById(int id);
    Optional<ResumePdf> findResumePdfByPersonId(int id);
}
