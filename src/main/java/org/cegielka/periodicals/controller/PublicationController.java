package org.cegielka.periodicals.controller;

import org.cegielka.periodicals.dto.PublicationRegistrationRequest;
import org.cegielka.periodicals.dto.UserRegistrationRequest;
import org.cegielka.periodicals.entity.Publication;
import org.cegielka.periodicals.entity.User;
import org.cegielka.periodicals.service.exception.UserNotFoundByIdException;
import org.cegielka.periodicals.service.impl.PublicationServiceImpl;
import org.cegielka.periodicals.service.impl.RegistrationServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
public class PublicationController {

    @Autowired
    private PublicationServiceImpl publicationService;

    @GetMapping("/publications")
    public String showUserList(Model model) {
        List<Publication> listPublications = publicationService.listAll();
        model.addAttribute("listPublications", listPublications);
        return "publications";
    }

    @GetMapping("/publications/new")
    public String showNewForm(Model model){
        model.addAttribute("publication",new PublicationRegistrationRequest());
        model.addAttribute("pageTitle","Add New Publication");
        return "publication_form";
    }

    @PostMapping("/publications/save")
    public String saveUser(PublicationRegistrationRequest publication, RedirectAttributes redirectAttributes){
        publicationService.register(publication);
        redirectAttributes.addFlashAttribute("message","The publication has been saved successfully.");
        return "redirect:/publications";
    }

    @GetMapping("/publications/edit/{id}")
    public String showEditForm(@PathVariable("id") Long id, Model model, RedirectAttributes redirectAttributes) {
        try {
            Publication publication = publicationService.get(id);
            model.addAttribute("publication", publication);
            model.addAttribute("pageTitle", "Edit publication (ID: " + id + ")");
            return "publication_form";
        } catch (UserNotFoundByIdException e) {
            redirectAttributes.addFlashAttribute("message", "The publication has been saved successfully");
            return "redirect:/publications";
        }
    }

    @GetMapping("/publications/delete/{id}")
    public String deleteUser(@PathVariable("id") Long id,RedirectAttributes redirectAttributes){
        try{
            publicationService.delete(id);
        }
        catch (UserNotFoundByIdException e){
            redirectAttributes.addFlashAttribute("message",e.getMessage());
        }
        return "redirect:/publications";
    }
}
