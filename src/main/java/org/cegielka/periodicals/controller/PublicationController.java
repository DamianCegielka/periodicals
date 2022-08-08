package org.cegielka.periodicals.controller;

import org.cegielka.periodicals.dto.PublicationRegistrationRequest;
import org.cegielka.periodicals.dto.SubscriptionRequest;
import org.cegielka.periodicals.entity.Publication;
import org.cegielka.periodicals.service.PublicationService;
import org.cegielka.periodicals.service.RegistrationService;
import org.cegielka.periodicals.service.exception.UserNotFoundByIdException;
import org.cegielka.periodicals.service.impl.PublicationServiceImpl;
import org.cegielka.periodicals.service.impl.RegistrationServiceImpl;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDateTime;
import java.util.List;

@Controller
@RequestMapping("/publications")
public class PublicationController {

    private PublicationService publicationService;
    private RegistrationService registrationService;

    public PublicationController(PublicationServiceImpl publicationService,
                                 RegistrationServiceImpl registrationService) {
        this.publicationService = publicationService;
        this.registrationService = registrationService;
    }

    @GetMapping("/subscription")
    public String showPublicationListToSubscription(Model model, @Param("keyword") String keyword,
                                                    @Param("sortField") String sortField,
                                                    @Param("sortDir") String sortDir) {
        //List<Publication> listPublications = publicationService.listAll();
        /*List<Publication> listPublications=publicationService.searchPublicationByTitle(keyword);
        model.addAttribute("listPublications", listPublications);
        return "publications_subscription";
         */
        return findPaginated(1, model, keyword, "id", "asc");
    }

    @GetMapping("/subscription/{id}")
    public String subscribe(@PathVariable("id") Long id, Long idUserFromSession, RedirectAttributes redirectAttributes) {
        try {
            SubscriptionRequest subscriptionRequest = new SubscriptionRequest();
            subscriptionRequest.setPublicationId(publicationService.get(id));
            subscriptionRequest.setUserId(registrationService.get(1l));//to change information about users from session
            subscriptionRequest.setDate(LocalDateTime.now());

            if (publicationService.addSubscription(subscriptionRequest)) {
                redirectAttributes.addFlashAttribute("message",
                        "The subscription has been added successfully.");
            } else {
                redirectAttributes.addFlashAttribute("message2",
                        "The subscription not added.You subscribe this position or your account is not active");
            }
        } catch (UserNotFoundByIdException e) {
            redirectAttributes.addFlashAttribute("message", e.getMessage());
        }
        return "redirect:/publications/subscription";
    }

    @GetMapping("/subscription/delete/{id}")
    public String deleteSubscription(@PathVariable("id") Long id, Long idUserFromSession, RedirectAttributes redirectAttributes) {
        try {
            SubscriptionRequest subscriptionRequest = new SubscriptionRequest();
            subscriptionRequest.setPublicationId(publicationService.get(id));
            subscriptionRequest.setUserId(registrationService.get(1L));//to change information about users from session

            if (publicationService.deleteSubscription(subscriptionRequest)) {
                redirectAttributes.addFlashAttribute("message", "The subscription has been deleted successfully.");
            } else {
                redirectAttributes.addFlashAttribute("message2", "The subscription not deleted.Probably you don't subscribe this position");
            }
        } catch (UserNotFoundByIdException e) {
            redirectAttributes.addFlashAttribute("message", e.getMessage());
        }
        return "redirect:/publications/subscription";
    }

    @GetMapping("/subscription/page/{pageNo}")
    String findPaginated(@PathVariable("pageNo") int pageNo, Model model, @Param("keyword") String keyword,
                         @Param("sortField") String sortField,
                         @Param("sortDir") String sortDir) {
        int pageSize = 5;

        Page<Publication> page = publicationService.findPaginatedWithSearching(pageNo, pageSize, keyword, sortField, sortDir);

        List<Publication> listPublications = page.getContent();
        model.addAttribute("currentPage", pageNo);
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("totalItems", page.getTotalElements());
        model.addAttribute("sortField", sortField);
        model.addAttribute("sortDir", sortDir);
        model.addAttribute("reverseSortDir", sortDir.equals("asc") ? "desc" : "asc");
        model.addAttribute("listPublications", listPublications);

        return "publications_subscription";
    }

    @GetMapping("/subscription/search")
    public List<Publication> searchPublication(@Param("keyword") String keyword) {
        return publicationService.searchPublicationByTitle(keyword);
    }

    @GetMapping("/new")
    public String showNewForm(Model model) {
        model.addAttribute("publication", new PublicationRegistrationRequest());
        model.addAttribute("pageTitle", "Add New Publication");
        return "publication_form";
    }

    @PostMapping("/save")
    public String saveUser(PublicationRegistrationRequest publication, RedirectAttributes redirectAttributes) {
        publicationService.register(publication);
        redirectAttributes.addFlashAttribute("message", "The publication has been saved successfully.");
        return "redirect:/publications/edit";
    }

    @GetMapping("/edit")
    public String showPublicationListToEdit(Model model) {
        List<Publication> listPublications = publicationService.listAll();
        model.addAttribute("listPublications", listPublications);
        return "publications_edit";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable("id") Long id, Model model, RedirectAttributes redirectAttributes) {
        try {
            Publication publication = publicationService.get(id);
            model.addAttribute("publication", publication);
            model.addAttribute("pageTitle", "Edit publication (ID: " + id + ")");
            return "publication_form";
        } catch (UserNotFoundByIdException e) {
            redirectAttributes.addFlashAttribute("message", e.getMessage());
            return "redirect:/publications/edit";
        }
    }

    @GetMapping("/delete/{id}")
    public String deleteUser(@PathVariable("id") Long id, RedirectAttributes redirectAttributes) {
        try {
            publicationService.delete(id);
        } catch (UserNotFoundByIdException e) {
            redirectAttributes.addFlashAttribute("message", e.getMessage());
        }
        return "redirect:/publications/edit";
    }
}
