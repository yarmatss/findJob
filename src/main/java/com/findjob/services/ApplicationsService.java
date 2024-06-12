package com.findjob.services;

import com.findjob.models.Application;
import com.findjob.models.Offer;
import com.findjob.repositories.ApplicationsRepository;
import com.findjob.repositories.OffersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class ApplicationsService {

    private final ApplicationsRepository applicationsRepository;
    private final OffersRepository offersRepository;

    @Autowired
    public ApplicationsService(ApplicationsRepository applicationsRepository, OffersRepository offersRepository) {
        this.applicationsRepository = applicationsRepository;
        this.offersRepository = offersRepository;
    }

    public List<Application> findAll() {
        return applicationsRepository.findAll();
    }

    public Application findById(int id) {
        Optional<Application> application = applicationsRepository.findById(id);
        return application.orElse(null);
    }

    public Application findApplicationByPersonId(int id) {
        Optional<Application> application = applicationsRepository.findApplicationByPersonId(id);
        return application.orElse(null);
    }

    public List<Application> findApplicationsByPersonId(int id) {
        return applicationsRepository.findApplicationsByPersonId(id);
    }

    public List<Application> findApplicationsByOfferId(int id) {
        return applicationsRepository.findApplicationsByOfferId(id);
    }

    @Transactional
    public void save(Application application) {
        applicationsRepository.save(application);
    }

    @Transactional
    public void update(int id, Application application) {
        application.setId(id);
        applicationsRepository.save(application);
    }

    @Transactional
    public void delete(int id) {
        applicationsRepository.deleteById(id);
    }

    @Transactional
    public void incrementCounter(int id) {
        Optional<Application> foundApplication = applicationsRepository.findById(id);
        if (foundApplication.isEmpty())
            return;

        Application application = foundApplication.get();
        application.getOffer().setApplicationCount(application.getOffer().getApplicationCount() + 1);
    }

    @Transactional
    public void decrementCounter(int id) {
        Optional<Application> foundApplication = applicationsRepository.findById(id);
        if (foundApplication.isEmpty())
            return;

        Application application = foundApplication.get();
        application.getOffer().setApplicationCount(application.getOffer().getApplicationCount() - 1);
    }

    @Transactional
    public void deleteApplicationsByOfferId(int id) {
        applicationsRepository.deleteApplicationByOfferId(id);
    }

    public List<Application> findByEmployerId(int id) {
//        List<Offer> offers = offersRepository.findOffersByEmployerId(id);
//        for (Offer offer : offers) {
//            System.out.println(offer.getId());
//        }

        return applicationsRepository.findAll();
    }
}
