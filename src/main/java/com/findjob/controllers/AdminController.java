package com.findjob.controllers;

import com.findjob.models.OfferCategory;
import com.findjob.models.Person;
import com.findjob.services.OfferCategoriesService;
import com.findjob.services.PeopleService;
import com.findjob.services.RegistrationService;
import com.findjob.util.PersonValidator;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final RegistrationService registrationService;
    private final PersonValidator personValidator;
    private final OfferCategoriesService offerCategoriesService;
    private final PeopleService peopleService;

    @Autowired
    public AdminController(RegistrationService registrationService, PersonValidator personValidator,
                           OfferCategoriesService offerCategoriesService, PeopleService peopleService) {
        this.registrationService = registrationService;
        this.personValidator = personValidator;
        this.offerCategoriesService = offerCategoriesService;
        this.peopleService = peopleService;
    }

    @GetMapping()
    public String admin() {
        return "admin/index";
    }

    @GetMapping("/registration")
    public String register(Model model) {
        Person person = new Person();
        person.setRole("ROLE_ADMIN");
        model.addAttribute("person", person);
        return "admin/registration";
    }

    @PostMapping("/registration")
    public String registration(@ModelAttribute("person") @Valid Person person,
                               BindingResult bindingResult) {
        personValidator.validate(person, bindingResult);
        if (bindingResult.hasErrors()) {
            bindingResult.getAllErrors().forEach(error -> System.out.println(error.getDefaultMessage()));
            return "admin/registration";
        }

        registrationService.register(person);

        return "redirect:/auth/login";
    }

    @GetMapping("/categories/add")
    public String addCategory(@ModelAttribute("offer_category") OfferCategory offerCategory) {
        return "admin/add_category";
    }

    @PostMapping("/categories/add")
    public String postCategory(@ModelAttribute("offer_category") @Valid OfferCategory offerCategory,
                               BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "admin/add_category";
        }

        offerCategoriesService.save(offerCategory);
        return "redirect:/admin/categories";
    }

    @GetMapping("/categories")
    public String categories(Model model) {
        model.addAttribute("offer_categories", offerCategoriesService.findAll());
        return "admin/categories";
    }

    @GetMapping("/category/{id}/edit")
    public String editCategory(@PathVariable int id, Model model) {
        model.addAttribute("offer_category", offerCategoriesService.findOfferCategoryById(id));
        return "admin/edit_category";
    }

    @PatchMapping("/category/{id}")
    public String updateCategory(@ModelAttribute("offer_category") OfferCategory offerCategory,
                                 @PathVariable int id) {
        offerCategoriesService.update(id, offerCategory);
        return "redirect:/admin/categories";
    }

    @DeleteMapping("/category/{id}")
    public String deleteCategory(@PathVariable int id) {
        offerCategoriesService.delete(id);
        return "redirect:/admin/categories";
    }

    @GetMapping("/employers")
    public String employers(Model model) {
        model.addAttribute("employers", peopleService.findAllEmployers());
        return "admin/employers";
    }

    @GetMapping("/candidates")
    public String candidates(Model model) {
        model.addAttribute("candidates", peopleService.findAllCandidates());
        return "admin/candidates";
    }
}
