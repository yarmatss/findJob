package com.findjob.services;

import com.findjob.models.Skill;
import com.findjob.repositories.SkillsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class SkillsService {
    private final SkillsRepository skillsRepository;

    @Autowired
    public SkillsService(SkillsRepository skillsRepository) {
        this.skillsRepository = skillsRepository;
    }

    public List<Skill> findAll() {
        return skillsRepository.findAll();
    }

    public Skill findById(int id) {
        Optional<Skill> skill = skillsRepository.findById(id);
        return skill.orElse(null);
    }

    @Transactional
    public Skill save(Skill skill) {
        return skillsRepository.save(skill);
    }

    @Transactional
    public void update(int id, Skill skill) {
        skill.setId(id);
        skillsRepository.save(skill);
    }

    @Transactional
    public void delete(int id) {
        skillsRepository.deleteById(id);
    }

    public List<Skill> findByPersonId(int personId) {
        return skillsRepository.findSkillsByPersonId(personId);
    }
}
