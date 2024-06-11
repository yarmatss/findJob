package com.findjob.services;

import com.findjob.models.OfferCategory;
import com.findjob.repositories.OfferCategoriesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class OfferCategoriesService {

    private final OfferCategoriesRepository offerCategoriesRepository;

    @Autowired
    public OfferCategoriesService(OfferCategoriesRepository offerCategoriesRepository) {
        this.offerCategoriesRepository = offerCategoriesRepository;
    }

    public List<OfferCategory> findAll() {
        return offerCategoriesRepository.findAll();
    }

    public OfferCategory findOfferCategoryById(int id) {
        Optional<OfferCategory> offerCategory = offerCategoriesRepository.findById(id);
        return offerCategory.orElse(null);
    }

    @Transactional
    public void save(OfferCategory offerCategory) {
        offerCategoriesRepository.save(offerCategory);
    }

    @Transactional
    public void update(int id, OfferCategory updatedOfferCategory) {
        updatedOfferCategory.setId(id);
        offerCategoriesRepository.save(updatedOfferCategory);
    }

    @Transactional
    public void delete(int id) {
        offerCategoriesRepository.deleteById(id);
    }
}
