package com.findjob.services;

import com.findjob.models.WorkExperience;
import com.findjob.repositories.WorkExperienceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class WorkExperienceService {
    private final WorkExperienceRepository workExperienceRepository;

    @Autowired
    public WorkExperienceService(WorkExperienceRepository workExperienceRepository) {
        this.workExperienceRepository = workExperienceRepository;
    }

    public List<WorkExperience> findAll() {
        return workExperienceRepository.findAll();
    }

    public WorkExperience findById(int id) {
        Optional<WorkExperience> workExperience = workExperienceRepository.findById(id);
        return workExperience.orElse(null);
    }

    @Transactional
    public WorkExperience save(WorkExperience workExperience) {
        return workExperienceRepository.save(workExperience);
    }

    @Transactional
    public void update(int id, WorkExperience workExperience) {
        workExperience.setId(id);
        workExperienceRepository.save(workExperience);
    }

    @Transactional
    public void delete(int id) {
        workExperienceRepository.deleteById(id);
    }

    public List<WorkExperience> findWorkExperienceByWorkExperienceId(int id) {
        return workExperienceRepository.findWorkExperienceByPersonId(id);
    }
}
