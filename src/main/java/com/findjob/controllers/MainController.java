package com.findjob.controllers;

import com.findjob.models.Person;
import com.findjob.services.OfferCategoriesService;
import com.findjob.services.OffersService;
import com.findjob.services.PeopleService;
import com.findjob.services.QualificationsService;
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

    @Autowired
    public MainController(OffersService offersService, OfferCategoriesService offerCategoriesService, PeopleService peopleService, QualificationsService qualificationsService) {
        this.offersService = offersService;
        this.offerCategoriesService = offerCategoriesService;
        this.peopleService = peopleService;
        this.qualificationsService = qualificationsService;
    }

    @GetMapping()
    public String index(Model model) {
        model.addAttribute("offerCategories", offerCategoriesService.findAll());
        model.addAttribute("offers", offersService.findAll());
        return "offer/show";
    }

    @GetMapping("/images/{id}/profile-picture")
    public ResponseEntity<byte[]> getProfilePicture(@PathVariable int id) {
        Person person = peopleService.findById(id);
        if (person == null || person.getProfilePicture() == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        byte[] imageContent = person.getProfilePicture().getPictureData();
        String contentType = person.getProfilePicture().getContentType();

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
}
