package com.findjob.controllers;

import com.findjob.models.*;
import com.findjob.security.PersonDetails;
import com.findjob.services.PeopleService;
import com.findjob.services.QualificationsService;
import com.findjob.services.SkillsService;
import com.findjob.services.WorkExperienceService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Controller
@RequestMapping("/profile")
public class ProfileController {
    private final PeopleService peopleService;
    private final QualificationsService qualificationsService;
    private final SkillsService skillsService;
    private final WorkExperienceService workExperienceService;

    @Autowired
    public ProfileController(PeopleService peopleService, QualificationsService qualificationsService, SkillsService skillsService, WorkExperienceService workExperienceService) {
        this.peopleService = peopleService;
        this.qualificationsService = qualificationsService;
        this.skillsService = skillsService;
        this.workExperienceService = workExperienceService;
    }

    @GetMapping()
    public String profile(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        PersonDetails personDetails = (PersonDetails) authentication.getPrincipal();
        model.addAttribute("person", personDetails.getPerson());
        model.addAttribute("userId", authentication.getName());
        return "redirect:/profile/" + personDetails.getPerson().getId();
    }

    @GetMapping("/{id}")
    public String show(@PathVariable int id, Model model) {
        model.addAttribute("person", peopleService.findById(id));
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUserId = authentication.getName(); // Assuming the user ID is stored as the username
        model.addAttribute("userId", currentUserId);
        model.addAttribute("qualifications", qualificationsService.findByPersonId(id));
        model.addAttribute("skills", skillsService.findByPersonId(id));
        model.addAttribute("work_experiences", workExperienceService.findWorkExperienceByWorkExperienceId(id));
        return "profile/show";
    }

    @GetMapping("/{id}/edit")
    public String edit(@PathVariable int id, Model model) {
        model.addAttribute("person", peopleService.findById(id));
        return "profile/edit";
    }

    @PatchMapping("/{id}")
    public String update(@PathVariable("id") int id, @Valid Person person, BindingResult bindingResult,
                                @RequestParam(value = "avatar_file", required = false) MultipartFile profilePictureFile,
                                @RequestParam(value = "resume_file", required = false) MultipartFile resumePdfFile) throws IOException {
        if (bindingResult.hasErrors()) {
            bindingResult.getAllErrors().forEach(error -> System.out.println(error.getDefaultMessage()));
            return "profile/edit";
        }

        // Check if a new profile picture was uploaded
        if (profilePictureFile != null && !profilePictureFile.isEmpty()) {
            ProfilePicture profilePicture = new ProfilePicture();
            profilePicture.setPictureData(profilePictureFile.getBytes());
            profilePicture.setContentType(profilePictureFile.getContentType());
            person.setProfilePicture(profilePicture);
            profilePicture.setPerson(person);
        }

        // Check if a new resume was uploaded
        if (resumePdfFile != null && !resumePdfFile.isEmpty()) {
            ResumePdf resumePdf = new ResumePdf();
            resumePdf.setResumeDataPdf(resumePdfFile.getBytes());
            person.setResumePdf(resumePdf);
            resumePdf.setPerson(person);
        }

        // If no new profile picture or resume was uploaded, retain the existing ones
        Person existingPerson = peopleService.findById(id);
        if (person.getProfilePicture() == null) {
            person.setProfilePicture(existingPerson.getProfilePicture());
        }

        if (person.getResumePdf() == null) {
            person.setResumePdf(existingPerson.getResumePdf());
        }

        peopleService.save(person);
        return "redirect:/profile/" + id;
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id) {
        peopleService.delete(id);
        return "redirect:/offer/show";
    }

    @GetMapping("/qualifications/add")
    public String addQualification(@ModelAttribute("qualification") Qualification qualification) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        PersonDetails personDetails = (PersonDetails) authentication.getPrincipal();
        qualification.setPerson(personDetails.getPerson());
        return "profile/add_qualification";
    }

    @PostMapping("/qualifications/add")
    public String postQualification(@ModelAttribute("qualification") Qualification qualification,
                               BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "profile/add_qualification";
        }

        qualificationsService.save(qualification);
        return "redirect:/profile/" + qualification.getPerson().getId();
    }

    @GetMapping("/skills/add")
    public String addSkill(@ModelAttribute("skill") Skill skill) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        PersonDetails personDetails = (PersonDetails) authentication.getPrincipal();
        skill.setPerson(personDetails.getPerson());
        return "profile/add_skill";
    }

    @PostMapping("/skills/add")
    public String postSkill(@ModelAttribute("skill") Skill skill,
                            BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "profile/add_skill";
        }
        skillsService.save(skill);
        return "redirect:/profile/" + skill.getPerson().getId();
    }

    @GetMapping("/work_experience/add")
    public String addWorkExperience(@ModelAttribute("work_experience") WorkExperience workExperience) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        PersonDetails personDetails = (PersonDetails) authentication.getPrincipal();
        workExperience.setPerson(personDetails.getPerson());
        return "profile/add_work_experience";
    }

    @PostMapping("/work_experience/add")
    public String postWorkExperience(@ModelAttribute("work_experience") WorkExperience workExperience,
                                     BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "profile/add_work_experience";
        }

        workExperienceService.save(workExperience);
        return "redirect:/profile/" + workExperience.getPerson().getId();
    }
}
