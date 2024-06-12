package com.findjob.repositories;

import com.findjob.models.Offer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OffersRepository extends JpaRepository<Offer, Integer> {
    List<Offer> findOffersByEmployerId(int id);
    List<Offer> findOffersByOfferCategoryIdAndOfferType(int offerCategoryId, String offerType);
}
