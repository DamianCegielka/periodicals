package org.cegielka.periodicals.controller;

import lombok.AllArgsConstructor;
import org.cegielka.periodicals.dto.LoggedUserIdAndRoleResponse;
import org.cegielka.periodicals.dto.PublicationAndGroupResponse;
import org.cegielka.periodicals.dto.PublicationRequest;
import org.cegielka.periodicals.entity.Accumulation;
import org.cegielka.periodicals.service.PublicationService;
import org.cegielka.periodicals.service.UserService;
import org.cegielka.periodicals.service.exception.PublicationNotAddException;
import org.cegielka.periodicals.service.exception.PublicationNotDeleteException;
import org.cegielka.periodicals.service.exception.PublicationOrGroupNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/publications")
@AllArgsConstructor
public class PublicationController {

    private static final String SUCCESS_MESSAGE = "success";
    private static final String ERROR_MESSAGE = "error";
    private static final String REDIRECT_PUBLICATIONS = "redirect:/publications/subscription";

    private final PublicationService publicationService;
    private final UserService userService;

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
        try {
            publicationService.add(publication);
            redirectAttributes.addFlashAttribute(SUCCESS_MESSAGE,
                    "The publication has been saved successfully.");
        } catch (PublicationNotAddException e) {
            redirectAttributes.addFlashAttribute(ERROR_MESSAGE, e.getMessage());
        }
        return REDIRECT_PUBLICATIONS;
    }

    @GetMapping("/edit/{id}")
    public String getEditForm(@PathVariable("id") Long idPublication, Model model,
                              RedirectAttributes redirectAttributes) {
        try {
            PublicationAndGroupResponse pubAndGroupResp = publicationService
                    .getPublicationAndAccumulation(idPublication);
            LoggedUserIdAndRoleResponse loggerUser = userService.getLoggerUser();
            model.addAttribute("idLoginUser", loggerUser.getId());
            model.addAttribute("userRole", loggerUser.getRole());
            model.addAttribute("listAccumulation", pubAndGroupResp.getListGroup());
            model.addAttribute("publication", pubAndGroupResp.getPublication());
            model.addAttribute("pageTitle", "Edit publication (ID: " + idPublication + ")");
            return "publication_form";
        } catch (PublicationOrGroupNotFoundException e) {
            redirectAttributes.addFlashAttribute(ERROR_MESSAGE, e.getMessage());
            return REDIRECT_PUBLICATIONS;
        }
    }

    @DeleteMapping("/delete/{id}")
    public String deletePublication(@PathVariable("id") Long idPublication, RedirectAttributes redirectAttributes) {
        try {
            publicationService.delete(idPublication);
            redirectAttributes.addFlashAttribute(SUCCESS_MESSAGE,
                    "The publication has been delete successfully.");
        } catch (PublicationNotDeleteException e) {
            redirectAttributes.addFlashAttribute(ERROR_MESSAGE, e.getMessage());
        }
        return REDIRECT_PUBLICATIONS;
    }
}
