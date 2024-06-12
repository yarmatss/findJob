package com.findjob.controllers;

import com.findjob.models.Application;
import com.findjob.models.Offer;
import com.findjob.models.Person;
import com.findjob.models.SearchCriteria;
import com.findjob.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class MainController {

    private final OffersService offersService;
    private final OfferCategoriesService offerCategoriesService;
    private final PeopleService peopleService;
    private final QualificationsService qualificationsService;
    private final ApplicationsService applicationsService;

    @Autowired
    public MainController(OffersService offersService, OfferCategoriesService offerCategoriesService, PeopleService peopleService, QualificationsService qualificationsService, ApplicationsService applicationsService) {
        this.offersService = offersService;
        this.offerCategoriesService = offerCategoriesService;
        this.peopleService = peopleService;
        this.qualificationsService = qualificationsService;
        this.applicationsService = applicationsService;
    }

    @GetMapping()
    public String index(Model model) {
        model.addAttribute("offerCategories", offerCategoriesService.findAll());
        model.addAttribute("offers", offersService.findAll());
        model.addAttribute("searchCriteria", new SearchCriteria());
        return "offer/show";
    }

    @GetMapping("/images/{id}/profile-picture")
    public ResponseEntity<byte[]> getProfilePicture(@PathVariable int id) {
        Person person = peopleService.findById(id);
        if (person == null || person.getPicture() == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        byte[] imageContent = person.getPicture().getPictureData();
        String contentType = person.getPicture().getContentType();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType(contentType));

        return new ResponseEntity<>(imageContent, headers, HttpStatus.OK);
    }

    @GetMapping("/images/{id}/company-logo")
    public ResponseEntity<byte[]> getCompanyLogo(@PathVariable int id) {
        Offer offer = offersService.findById(id);
        if (offer == null || offer.getCompanyLogo() == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        byte[] imageContent = offer.getCompanyLogo().getPictureData();
        String contentType = offer.getCompanyLogo().getContentType();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType(contentType));

        return new ResponseEntity<>(imageContent, headers, HttpStatus.OK);
    }

    @GetMapping("/resume/{id}")
    public ResponseEntity<byte[]> downloadResume(@PathVariable("id") int id) {
        Person person = peopleService.findById(id);
        if (person == null || person.getResumePdf() == null) {
            return ResponseEntity.notFound().build();
        }

        byte[] resource = person.getResumePdf().getResumeDataPdf();

        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + person.getFirstName() + "_" + person.getLastName() + "_resume.pdf");

        return ResponseEntity.ok()
                .headers(headers)
                .contentType(MediaType.APPLICATION_PDF)
                .body(resource);
    }

    @GetMapping("/task/{id}")
    public ResponseEntity<byte[]> downloadSolution(@PathVariable("id") int id) {
        Application application = applicationsService.findById(id);
        if (application == null || application.getResumePdf() == null) {
            return ResponseEntity.notFound().build();
        }

        byte[] resource = application.getResumePdf().getResumeDataPdf();

        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" +
                application.getPerson().getFirstName() + '_' +
                application.getPerson().getLastName() + "_solution.pdf");

        return ResponseEntity.ok()
                .headers(headers)
                .contentType(MediaType.APPLICATION_PDF)
                .body(resource);
    }

    @GetMapping("/task/offer/{id}")
    public ResponseEntity<byte[]> downloadTask(@PathVariable("id") int id) {
        Offer offer = offersService.findById(id);
        if (offer == null || offer.getResumePdf() == null) {
            return ResponseEntity.notFound().build();
        }

        byte[] resource = offer.getResumePdf().getResumeDataPdf();

        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" +
                 offer.getTitle() + "_task.pdf");

        return ResponseEntity.ok()
                .headers(headers)
                .contentType(MediaType.APPLICATION_PDF)
                .body(resource);
    }
}
