package com.findjob.controllers;

import com.findjob.models.Application;
import com.findjob.models.Offer;
import com.findjob.models.SearchCriteria;
import com.findjob.security.PersonDetails;
import com.findjob.services.OfferCategoriesService;
import com.findjob.services.OffersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

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
        model.addAttribute("searchCriteria", new SearchCriteria());
        return "offer/show";
    }

    @GetMapping("/{id}")
    public String details(@PathVariable("id") int id, Model model) {
        Offer offer = offersService.findById(id);
        model.addAttribute("offer", offer);
        Application application = new Application();
        application.setOffer(offer);
        application.setApplicationDate(LocalDateTime.now());

        model.addAttribute("application", application);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            PersonDetails personDetails = (PersonDetails) authentication.getPrincipal();
            model.addAttribute("person", personDetails.getPerson());
        }

        return "offer/details";
    }

    @GetMapping("/search")
    public String search(Model model, @ModelAttribute("searchCriteria") SearchCriteria searchCriteria) {
        model.addAttribute("offers", offersService.searchOffers(searchCriteria));
        model.addAttribute("offerCategories", offerCategoriesService.findAll());

        return "offer/show";
    }

    @GetMapping("/{id}/task")
    public ResponseEntity<byte[]> downloadResume(@PathVariable("id") int id) {
        Offer offer = offersService.findById(id);
        if (offer == null || offer.getResumePdf() == null) {
            return ResponseEntity.notFound().build();
        }

        byte[] resource = offer.getResumePdf().getResumeDataPdf();

        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + offer.getTitle() + "_task.pdf");

        return ResponseEntity.ok()
                .headers(headers)
                .contentType(MediaType.APPLICATION_PDF)
                .body(resource);
    }
}
