package com.findjob.controllers;

import com.findjob.models.Application;
import com.findjob.models.Offer;
import com.findjob.models.Person;
import com.findjob.models.ResumePdf;
import com.findjob.services.ApplicationsService;
import com.findjob.services.OffersService;
import com.findjob.services.PeopleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;

@Controller
@RequestMapping("/candidate")
public class CandidateController {
    private final PeopleService peopleService;
    private final ApplicationsService applicationsService;
    private final OffersService offersService;

    @Autowired
    public CandidateController(PeopleService peopleService, ApplicationsService applicationsService, OffersService offersService) {
        this.peopleService = peopleService;
        this.applicationsService = applicationsService;
        this.offersService = offersService;
    }

    @PostMapping("/{id}")
    public String apply(@PathVariable("id") int id,
                        @RequestParam(value = "resume_file", required = false) MultipartFile resumePdfFile) throws IOException {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username;
        if (principal instanceof UserDetails) {
            username = ((UserDetails) principal).getUsername();
        } else {
            username = principal.toString();
        }

        Person person = peopleService.findByUsername(username);
        Offer offer = offersService.findById(id);

        Application application = new Application();
        application.setPerson(person);
        application.setOffer(offer);
        application.setApplicationDate(LocalDateTime.now());
        application.setStatus("Applied");
        offer.setApplicationCount(offer.getApplicationCount() + 1);

        // Check if a new resume was uploaded
        if (resumePdfFile != null && !resumePdfFile.isEmpty()) {
            ResumePdf resumePdf = new ResumePdf();
            resumePdf.setResumeDataPdf(resumePdfFile.getBytes());
            application.setResumePdf(resumePdf);
            resumePdf.setApplication(application);
        }

        applicationsService.save(application);

        return "redirect:/offer/show";
    }

    @GetMapping("/applications")
    public String applications(Model model) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username;
        if (principal instanceof UserDetails) {
            username = ((UserDetails) principal).getUsername();
        } else {
            username = principal.toString();
        }

        Person person = peopleService.findByUsername(username);
        model.addAttribute("applications", applicationsService.findApplicationsByPersonId(person.getId()));

        return "candidate/applications";
    }

    @PostMapping("/{id}/cancel")
    public String cancelApplication(@PathVariable("id") int id) {
//        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//        String username;
//        if (principal instanceof UserDetails) {
//            username = ((UserDetails) principal).getUsername();
//        } else {
//            username = principal.toString();
//        }
//
//        Person person = peopleService.findByUsername(username);

        Application application = applicationsService.findById(id);
        applicationsService.decrementCounter(id);
        application.setStatus("Cancelled");

        applicationsService.save(application);

        return "redirect:/candidate/applications";
    }
}
