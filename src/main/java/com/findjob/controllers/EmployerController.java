package com.findjob.controllers;

import com.findjob.models.Offer;
import com.findjob.services.OfferCategoriesService;
import com.findjob.services.OffersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/employer")
public class EmployerController {

    private final OffersService offersService;
    private final OfferCategoriesService offerCategoriesService;

    @Autowired
    public EmployerController(OffersService offersService, OfferCategoriesService offerCategoriesService) {
        this.offersService = offersService;
        this.offerCategoriesService = offerCategoriesService;
    }

    @GetMapping("/offers/add")
    public String addOffer(Model model) {
        model.addAttribute("categories", offerCategoriesService.findAll());
        model.addAttribute("offer", new Offer());
        return "employer/add_offer";
    }

    @PostMapping("/offers/add")
    public String addOfferSubmit(@ModelAttribute("offer") Offer offer) {
        offersService.save(offer);
        return "redirect:/offer/show";
    }
}
