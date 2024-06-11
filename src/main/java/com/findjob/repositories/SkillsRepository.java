package com.findjob.repositories;

import com.findjob.models.Skill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SkillsRepository extends JpaRepository<Skill, Integer> {
    List<Skill> findSkillsByPersonId(int id);
}
