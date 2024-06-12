package com.findjob.controllers;

import com.findjob.models.*;
import com.findjob.security.PersonDetails;
import com.findjob.services.ApplicationsService;
import com.findjob.services.OfferCategoriesService;
import com.findjob.services.OffersService;
import com.findjob.services.PicturesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;

@Controller
@RequestMapping("/employer")
public class EmployerController {

    private final OffersService offersService;
    private final OfferCategoriesService offerCategoriesService;
    private final PicturesService picturesService;
    private final ApplicationsService applicationsService;

    @Autowired
    public EmployerController(OffersService offersService, OfferCategoriesService offerCategoriesService, PicturesService picturesService, ApplicationsService applicationsService) {
        this.offersService = offersService;
        this.offerCategoriesService = offerCategoriesService;
        this.picturesService = picturesService;
        this.applicationsService = applicationsService;
    }

    @GetMapping("/offers/add")
    public String addOffer(Model model) {
        model.addAttribute("categories", offerCategoriesService.findAll());
        model.addAttribute("offer", new Offer());
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        PersonDetails personDetails = (PersonDetails) authentication.getPrincipal();
        model.addAttribute("person", personDetails.getPerson());
        return "employer/add_offer";
    }

    @PostMapping("/offers/add")
    public String addOfferSubmit(@ModelAttribute("offer") Offer offer, BindingResult bindingResult,
                                 @RequestParam(value = "company_logo", required = false) MultipartFile companyLogoFile,
                                 @RequestParam(value = "resume_file", required = false) MultipartFile resumePdfFile) throws IOException {
        if (bindingResult.hasErrors()) {
            bindingResult.getAllErrors().forEach(error -> System.out.println(error.getDefaultMessage()));
            return "employer/add_offer";
        }

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        PersonDetails personDetails = (PersonDetails) authentication.getPrincipal();
        Person person = personDetails.getPerson();

        offer.setPostedAt(LocalDateTime.now());
        offer.setApplicationCount(0);
        offer.setEmployer(person);

        // Check if a new company logo was uploaded
        if (companyLogoFile != null && !companyLogoFile.isEmpty()) {
            Picture pic = new Picture();
            pic.setPictureData(companyLogoFile.getBytes());
            pic.setContentType(companyLogoFile.getContentType());
            offer.setCompanyLogo(pic);
            picturesService.save(pic);
        }

        // Check if a new resume was uploaded
        if (resumePdfFile != null && !resumePdfFile.isEmpty()) {
            ResumePdf resumePdf = new ResumePdf();
            resumePdf.setResumeDataPdf(resumePdfFile.getBytes());
            offer.setResumePdf(resumePdf);
            resumePdf.setOffer(offer);
        }

//        // If no new profile picture or resume was uploaded, retain the existing ones
//        Offer existingOffer = offersService.findById(id);
//        if (offer.getCompanyLogo() == null) {
//            offer.setCompanyLogo(existingOffer.getCompanyLogo());
//        }
//
//        if (offer.getResumePdf() == null) {
//            offer.setResumePdf(existingOffer.getResumePdf());
//        }

        offersService.save(offer);
        return "redirect:/offer/show";
    }

    @GetMapping("/offers/show")
    public String showOffers(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        PersonDetails personDetails = (PersonDetails) authentication.getPrincipal();
        Person person = personDetails.getPerson();
        model.addAttribute("offers", offersService.findOffersByEmployerId(person.getId()));
        model.addAttribute("offerCategories", offerCategoriesService.findAll());
        return "employer/offers_show";
    }

    @GetMapping("/offer/{id}")
    public String showApplications(@PathVariable("id") int id, Model model) {
        model.addAttribute("applications", applicationsService.findApplicationsByOfferId(id));

        return "employer/offer_applications";
    }

    @DeleteMapping("/offer/{id}")
    public String deleteApplication(@PathVariable("id") int id) {
        applicationsService.deleteApplicationsByOfferId(id);
        offersService.delete(id);
        return "employer/offer_applications";
    }

    @GetMapping("/application/{id}/status")
    public String getStatus(@PathVariable("id") int id, Model model) {
        Application application = applicationsService.findById(id);
        model.addAttribute("my_application", application);

        return "employer/update_status";
    }

    @PostMapping("/application/{id}/status")
    public String updateStatus(@PathVariable("id") int id, @RequestParam("status") String status) {
        Application application = applicationsService.findById(id);
        application.setStatus(status);
        applicationsService.save(application);

        return "redirect:/employer/offer/" + application.getOffer().getId();
    }

}
