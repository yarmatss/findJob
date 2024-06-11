package com.findjob.controllers;

import com.findjob.models.Offer;
import com.findjob.services.OfferCategoriesService;
import com.findjob.services.OffersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/offer")
public class OffersController {

    private final OffersService offersService;
    private final OfferCategoriesService offerCategoriesService;

    @Autowired
    public OffersController(OffersService offersService, OfferCategoriesService offerCategoriesService) {
        this.offersService = offersService;
        this.offerCategoriesService = offerCategoriesService;
    }

    @GetMapping("/show")
    public String index(Model model) {
        model.addAttribute("offerCategories", offerCategoriesService.findAll());
        model.addAttribute("offers", offersService.findAll());
        return "offer/show";
    }


}
