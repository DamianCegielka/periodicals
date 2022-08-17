package org.cegielka.periodicals.controller;

import lombok.AllArgsConstructor;
import org.cegielka.periodicals.dto.PublicationRequest;
import org.cegielka.periodicals.entity.Accumulation;
import org.cegielka.periodicals.entity.Publication;
import org.cegielka.periodicals.service.PublicationService;
import org.cegielka.periodicals.service.UserService;
import org.cegielka.periodicals.service.exception.UserNotFoundByIdException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/publications")
@AllArgsConstructor
public class PublicationController {
    private final PublicationService publicationService;
    private final UserService userService;
    private static final String MESSAGE = "MESSAGE";
    private static final String REDIRECT_PUBLICATIONS = "redirect:/publications/subscription";


    @GetMapping("/new")
    public String getNewForm(Model model) {
        List<Accumulation> listAccumulation = publicationService.getAllAccumulation();
        model.addAttribute("listAccumulation", listAccumulation);
        model.addAttribute("publication", new PublicationRequest());
        model.addAttribute("pageTitle", "Add New Publication");
        return "publication_form";
    }

    @PostMapping("/save")
    public String saveUser(PublicationRequest publication, RedirectAttributes redirectAttributes) {
        System.out.println(publication);
        System.out.println(publication.getAccumulation());
        publicationService.add(publication);
        redirectAttributes.addFlashAttribute(MESSAGE, "The publication has been saved successfully.");
        return REDIRECT_PUBLICATIONS;
    }

    @GetMapping("/edit/{id}")
    public String getEditForm(@PathVariable("id") Long id, Model model, RedirectAttributes redirectAttributes) {
        try {
            Publication publication = publicationService.get(id);
            List<Accumulation> listAccumulation = publicationService.getAllAccumulation();
            Long numberIdForLoginUser = userService.getIdUserWhichIsLogin();
            String roleForLoginUser = userService.getUserRoleWhichIsLogin();
            model.addAttribute("idLoginUser", numberIdForLoginUser);
            model.addAttribute("userRole", roleForLoginUser);
            model.addAttribute("listAccumulation", listAccumulation);
            model.addAttribute("publication", publication);
            model.addAttribute("pageTitle", "Edit publication (ID: " + id + ")");
            return "publication_form";
        } catch (UserNotFoundByIdException e) {
            redirectAttributes.addFlashAttribute(MESSAGE, e.getMessage());
            return REDIRECT_PUBLICATIONS;
        }
    }

    @GetMapping("/delete/{id}")
    public String deleteUser(@PathVariable("id") Long id, RedirectAttributes redirectAttributes) {
        try {
            publicationService.delete(id);
        } catch (UserNotFoundByIdException e) {
            redirectAttributes.addFlashAttribute(MESSAGE, e.getMessage());
        }
        return REDIRECT_PUBLICATIONS;
    }
}
