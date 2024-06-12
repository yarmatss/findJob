package com.findjob.repositories;

import com.findjob.models.Application;
import com.findjob.models.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ApplicationsRepository extends JpaRepository<Application, Integer> {
    Optional<Application> findApplicationByPersonId(int id);
    Optional<Application> findById(int id);
    List<Application> findApplicationsByPersonId(int id);
    List<Application> findApplicationsByOfferId(int id);
    void deleteApplicationByOfferId(int id);
}
