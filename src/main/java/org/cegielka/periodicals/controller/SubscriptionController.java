package org.cegielka.periodicals.controller;

import lombok.AllArgsConstructor;
import org.cegielka.periodicals.dto.SubscriptionRequest;
import org.cegielka.periodicals.entity.Publication;
import org.cegielka.periodicals.service.PublicationService;
import org.cegielka.periodicals.service.SubscriptionService;
import org.cegielka.periodicals.service.UserService;
import org.cegielka.periodicals.service.exception.UserNotFoundByIdException;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDateTime;
import java.util.List;

@Controller
@RequestMapping("publications/subscription")
@AllArgsConstructor
public class SubscriptionController {

    private PublicationService publicationService;
    private UserService userService;

    SubscriptionService subscriptionService;

    @GetMapping("")
    public String showPublicationListToSubscription(Model model,
                                                    @Param("keyword") String keyword,
                                                    @Param("groupValue") Long groupValue) {

        return findPaginated(model, 1, keyword, "id", "asc", groupValue);
    }

    @GetMapping("/page/{pageNo}")
    String findPaginated(Model model,
                         @PathVariable("pageNo") int pageNo,
                         @Param("keyword") String keyword,
                         @Param("sortField") String sortField,
                         @Param("sortDir") String sortDir,
                         @Param("groupValue") Long groupValue) {
        try {
            int pageSize = 5;
            Page<Publication> page = publicationService.findPaginatedWithSearching(pageNo, pageSize, keyword, sortField, sortDir, groupValue);
            List<Publication> listPublications = page.getContent();
            Long numberIdForLoginUser = userService.getIdUserWhichIsLogin();
            String roleForLoginUser = userService.getUserRoleWhichIsLogin();
            model.addAttribute("currentPage", pageNo);
            model.addAttribute("totalPages", page.getTotalPages());
            model.addAttribute("totalItems", page.getTotalElements());
            model.addAttribute("sortField", sortField);
            model.addAttribute("sortDir", sortDir);
            model.addAttribute("reverseSortDir", sortDir.equals("asc") ? "desc" : "asc");
            model.addAttribute("listPublications", listPublications);
            model.addAttribute("idLoginUser", numberIdForLoginUser);
            model.addAttribute("userRole", roleForLoginUser);
        } catch (RuntimeException e) {
            e.printStackTrace();
        }
        return "publications_subscription";
    }

    @GetMapping("/delete/{id}")
    public String deleteSubscription(@PathVariable("id") Long id, RedirectAttributes redirectAttributes) {
        try {
            SubscriptionRequest subscriptionRequest = new SubscriptionRequest();
            subscriptionRequest.setPublicationId(publicationService.get(id));
            subscriptionRequest.setUserId(userService.get(userService.getIdUserWhichIsLogin()));
            if (subscriptionService.deleteSubscription(subscriptionRequest)) {
                redirectAttributes.addFlashAttribute("message", "The subscription has been deleted successfully.");
            } else {
                redirectAttributes.addFlashAttribute("message2", "The subscription not deleted.Probably you don't subscribe this position");
            }
        } catch (UserNotFoundByIdException e) {
            redirectAttributes.addFlashAttribute("message", e.getMessage());
        }
        return "redirect:/publications/subscription";
    }

    @GetMapping("/{id}")
    public String subscribe(@PathVariable("id") Long id, RedirectAttributes redirectAttributes) {
        try {
            SubscriptionRequest subscriptionRequest = new SubscriptionRequest();
            subscriptionRequest.setPublicationId(publicationService.get(id));
            subscriptionRequest.setUserId(userService.get(userService.getIdUserWhichIsLogin()));
            subscriptionRequest.setDate(LocalDateTime.now());
            if (subscriptionService.addSubscription(subscriptionRequest)) {
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

    @GetMapping("/subscription/search")
    public List<Publication> searchPublication(@Param("keyword") String keyword) {
        return publicationService.searchPublicationByTitle(keyword);
    }
}
