package com.findjob.services;

import com.findjob.models.Qualification;
import com.findjob.repositories.QualificationsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class QualificationsService {

    private final QualificationsRepository qualificationsRepository;

    @Autowired
    public QualificationsService(QualificationsRepository qualificationsRepository) {
        this.qualificationsRepository = qualificationsRepository;
    }

    public List<Qualification> findAll() {
        return qualificationsRepository.findAll();
    }

    public Qualification findById(int id) {
        Optional<Qualification> qualification = qualificationsRepository.findById(id);
        return qualification.orElse(null);
    }

    public List<Qualification> findByPersonId(int id) {
        return qualificationsRepository.findQualificationsByPersonId(id);
    }

    @Transactional
    public Qualification save(Qualification qualification) {
        return qualificationsRepository.save(qualification);
    }

    @Transactional
    public void update(int id, Qualification qualification) {
        qualification.setId(id);
        qualificationsRepository.save(qualification);
    }

    @Transactional
    public void delete(int id) {
        qualificationsRepository.deleteById(id);
    }
}
