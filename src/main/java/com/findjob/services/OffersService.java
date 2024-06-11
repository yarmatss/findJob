package com.findjob.services;

import com.findjob.models.Offer;
import com.findjob.repositories.OffersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class OffersService {

    private final OffersRepository offersRepository;

    @Autowired
    public OffersService(OffersRepository offersRepository) {
        this.offersRepository = offersRepository;
    }

    public List<Offer> findAll() {
        return offersRepository.findAll();
    }

    public Offer findById(int id) {
        Optional<Offer> offer = offersRepository.findById(id);
        return offer.orElse(null);
    }

    @Transactional
    public void save(Offer offer) {
        offersRepository.save(offer);
    }

    @Transactional
    public void update(int id, Offer updatedOffer) {
        updatedOffer.setId(id);
        offersRepository.save(updatedOffer);
    }

    @Transactional
    public void delete(int id) {
        offersRepository.deleteById(id);
    }
}
