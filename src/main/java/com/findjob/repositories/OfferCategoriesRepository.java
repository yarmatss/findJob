package com.findjob.repositories;

import com.findjob.models.OfferCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OfferCategoriesRepository extends JpaRepository<OfferCategory, Integer> {
}
