package com.findjob.repositories;

import com.findjob.models.Qualification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QualificationsRepository extends JpaRepository<Qualification, Integer> {
    List<Qualification> findQualificationsByPersonId(int personId);
}
