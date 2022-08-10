package org.cegielka.periodicals.controller;

import lombok.AllArgsConstructor;
import org.cegielka.periodicals.dto.UserRegistrationRequest;
import org.cegielka.periodicals.entity.Subscription;
import org.cegielka.periodicals.entity.User;
import org.cegielka.periodicals.service.PublicationService;
import org.cegielka.periodicals.service.SubscriptionService;
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
@RequestMapping("/users")
@AllArgsConstructor
public class UserController {

    private UserService userService;
    private PublicationService publicationService;
    private SubscriptionService subscriptionService;

    @GetMapping(" ")
    public String showUserList(Model model) {
        List<User> listUsers = userService.listAll();
        Long numberIdForLoginUser = userService.getIdUserWhichIsLogin();
        String roleForLoginUser = userService.getUserRoleWhichIsLogin();
        model.addAttribute("idLoginUser", numberIdForLoginUser);
        model.addAttribute("userRole", roleForLoginUser);
        model.addAttribute("listUsers", listUsers);
        return "users";
    }

    @GetMapping("/register")
    public String registerNewUser(Model model) {
        model.addAttribute("user", new UserRegistrationRequest());
        model.addAttribute("pageTitle", "Register");
        return "register_form";
    }

    @PostMapping("/save")
    public String saveUser(UserRegistrationRequest user, RedirectAttributes redirectAttributes) {
        userService.register(user);
        redirectAttributes.addFlashAttribute("message", "The user has been saved successfully.");
        return "redirect:/users";
    }

    @GetMapping("/edit/{id}")
    public String changeActiveState(@PathVariable("id") Long id, RedirectAttributes redirectAttributes, Model model) {
        try {

            Long numberIdForLoginUser = userService.getIdUserWhichIsLogin();
            String roleForLoginUser = userService.getUserRoleWhichIsLogin();
            model.addAttribute("idLoginUser", numberIdForLoginUser);
            model.addAttribute("userRole", roleForLoginUser);
            userService.updateState(id);
            redirectAttributes.addFlashAttribute("message", "State for id " + id + " has been changed successfully.");
        } catch (UserNotFoundByIdException e) {
            redirectAttributes.addFlashAttribute("message", e.getMessage());
        }
        return "redirect:/users";
    }

    @GetMapping("/subscription")
    public String showSubscriptionByUser(Model model) {
        List<Subscription> listPublications = subscriptionService
                .showSubscriptionsSubscribingByUser(userService.getIdUserWhichIsLogin());
        Long numberIdForLoginUser = userService.getIdUserWhichIsLogin();
        String roleForLoginUser = userService.getUserRoleWhichIsLogin();
        model.addAttribute("idLoginUser", numberIdForLoginUser);
        model.addAttribute("userRole", roleForLoginUser);
        model.addAttribute("listPublications", listPublications);
        return "user_subscription";
    }
}
