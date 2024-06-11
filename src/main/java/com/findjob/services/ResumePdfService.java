package com.findjob.services;

import com.findjob.models.ResumePdf;
import com.findjob.repositories.ResumePdfRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional(readOnly=true)
public class ResumePdfService {

    private final ResumePdfRepository resumePdfRepository;

    @Autowired
    public ResumePdfService(ResumePdfRepository resumePdfRepository) {
        this.resumePdfRepository = resumePdfRepository;
    }

    ResumePdf getResumePdf(int id) {
        Optional<ResumePdf> resumePdf = resumePdfRepository.findById(id);
        return resumePdf.orElse(null);
    }

    ResumePdf getResumePdfByPersonId(int personId) {
        Optional<ResumePdf> resumePdf = resumePdfRepository.findResumePdfByPersonId(personId);
        return resumePdf.orElse(null);
    }

    @Transactional
    public ResumePdf save(ResumePdf resumePdf) {
        return resumePdfRepository.save(resumePdf);
    }

    @Transactional
    public void update(int id, ResumePdf resumePdf) {
        resumePdf.setId(id);
        resumePdfRepository.save(resumePdf);
    }

    @Transactional
    public void delete(int id) {
        resumePdfRepository.deleteById(id);
    }
}
