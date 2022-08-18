package org.cegielka.periodicals.controller;

import lombok.AllArgsConstructor;
import org.cegielka.periodicals.dto.LoggedUserIdAndRoleResponse;
import org.cegielka.periodicals.dto.PaginationRequest;
import org.cegielka.periodicals.entity.Publication;
import org.cegielka.periodicals.service.PublicationService;
import org.cegielka.periodicals.service.SubscriptionService;
import org.cegielka.periodicals.service.UserService;
import org.cegielka.periodicals.service.exception.PaginationNotExecuteException;
import org.cegielka.periodicals.service.exception.SubscriptionNotAddException;
import org.cegielka.periodicals.service.exception.SubscriptionNotDeleteException;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("publications/subscription")
@AllArgsConstructor
public class SubscriptionController {

    private static final String SUCCESS_MESSAGE = "success";
    private static final String ERROR_MESSAGE = "error";

    private final PublicationService publicationService;
    private final UserService userService;
    private final SubscriptionService subscriptionService;


    @GetMapping("")
    public String getPublicationListToSubscription(Model model,
                                                   @Param("keyword") String keyword,
                                                   @Param("groupValue") Long groupValue,
                                                   RedirectAttributes redirectAttributes) {

        return findPaginated(model, 1, keyword, "id", "asc", groupValue, redirectAttributes);
    }

    @GetMapping("/page/{pageNo}")
    String findPaginated(Model model,
                         @PathVariable("pageNo") int pageNo,
                         @Param("keyword") String keyword,
                         @Param("sortField") String sortField,
                         @Param("sortDir") String sortDir,
                         @Param("groupValue") Long groupValue,
                         RedirectAttributes redirectAttributes) {
        try {

            int pageSize = 5;
            PaginationRequest request = new PaginationRequest();
            request.setPageNo(pageNo);
            request.setPageSize(pageSize);
            request.setSortField(sortField);
            request.setSortDirection(sortDir);
            request.setGroupValue(groupValue);

            Page<Publication> page = publicationService.findPaginatedWithSearching(request);
            LoggedUserIdAndRoleResponse loggedUser = userService.getLoggerUser();
            model.addAttribute("currentPage", pageNo);
            model.addAttribute("totalPages", page.getTotalPages());
            model.addAttribute("totalItems", page.getTotalElements());
            model.addAttribute("sortField", sortField);
            model.addAttribute("sortDir", sortDir);
            model.addAttribute("reverseSortDir", sortDir.equals("asc") ? "desc" : "asc");
            model.addAttribute("listPublications", page.getContent());
            model.addAttribute("idLoginUser", loggedUser.getId());
            model.addAttribute("userRole", loggedUser.getRole());
            model.addAttribute("keyword", keyword);
            model.addAttribute("groupValue", groupValue);
        } catch (PaginationNotExecuteException e) {
            redirectAttributes.addFlashAttribute(ERROR_MESSAGE, e.getMessage());
        }
        return "publications_subscription";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable("id") Long idPublication, RedirectAttributes redirectAttributes) {
        try {
            if (subscriptionService.deleteSubscriptionByCurrentUser(idPublication)) {
                redirectAttributes.addFlashAttribute(SUCCESS_MESSAGE,
                        "The subscription has been deleted successfully.");
            } else {
                redirectAttributes.addFlashAttribute(ERROR_MESSAGE,
                        "The subscription not deleted. Probably you don't subscribe this position");
            }
        } catch (SubscriptionNotDeleteException e) {
            redirectAttributes.addFlashAttribute(ERROR_MESSAGE, e.getMessage());
        }
        return "redirect:/publications/subscription";
    }

    @GetMapping("/{id}")
    public String subscribe(@PathVariable("id") Long idPublication, RedirectAttributes redirectAttributes) {
        try {
            if (subscriptionService.addSubscriptionByCurrentUser(idPublication)) {
                redirectAttributes.addFlashAttribute(SUCCESS_MESSAGE,
                        "The subscription has been added successfully.");
            } else {
                redirectAttributes.addFlashAttribute(ERROR_MESSAGE,
                        "You subscribe this position or your account is not active");
            }
        } catch (SubscriptionNotAddException e) {
            redirectAttributes.addFlashAttribute(ERROR_MESSAGE, e.getMessage());
        }
        return "redirect:/publications/subscription";
    }

    @GetMapping("/subscription/search")
    public List<Publication> searchPublication(@Param("keyword") String keyword) {
        return publicationService.searchPublicationByTitle(keyword);
    }
}