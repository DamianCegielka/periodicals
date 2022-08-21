package org.cegielka.periodicals.controller;

import lombok.AllArgsConstructor;
import org.cegielka.periodicals.dto.LoggedUserIdAndRoleResponse;
import org.cegielka.periodicals.dto.UserRegistrationRequest;
import org.cegielka.periodicals.entity.Subscription;
import org.cegielka.periodicals.entity.User;
import org.cegielka.periodicals.service.SubscriptionService;
import org.cegielka.periodicals.service.UserService;
import org.cegielka.periodicals.service.exception.UserNotFoundByIdException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/users")
@AllArgsConstructor
public class UserController {

    private static final String SUCCESS_MESSAGE = "success";
    private static final String ERROR_MESSAGE = "error";
    private static final String ID_LOGIN_USER = "idLoginUser";
    private static final String USER_ROLE = "userRole";

    private final UserService userService;
    private final SubscriptionService subscriptionService;


    @GetMapping("")
    public String getUserList(Model model) {
        List<User> listUsers = userService.listAll();
        LoggedUserIdAndRoleResponse loggedUser = userService.getLoggerUser();
        model.addAttribute(ID_LOGIN_USER, loggedUser.getId());
        model.addAttribute(USER_ROLE, loggedUser.getRole());
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
    public String saveUser(@Valid @ModelAttribute("user") UserRegistrationRequest user, RedirectAttributes redirectAttributes) {
        try {
            userService.register(user);
            redirectAttributes.addFlashAttribute(SUCCESS_MESSAGE, "The user has been saved successfully.");
            return "redirect:/login";

        } catch (RuntimeException e) {
            redirectAttributes.addFlashAttribute(ERROR_MESSAGE, e.getMessage());
            return "redirect:/users/register";

        }
    }

    @GetMapping("/edit/{id}")
    public String reverseActiveState(@PathVariable("id") Long id, RedirectAttributes redirectAttributes, Model model) {
        try {
            LoggedUserIdAndRoleResponse loggedUser = userService.getLoggerUser();
            model.addAttribute(ID_LOGIN_USER, loggedUser.getId());
            model.addAttribute(USER_ROLE, loggedUser.getRole());
            userService.reverseState(id);
            redirectAttributes.addFlashAttribute(SUCCESS_MESSAGE,
                    "State for id " + id + " has been changed successfully.");
        } catch (UserNotFoundByIdException e) {
            redirectAttributes.addFlashAttribute(ERROR_MESSAGE, e.getMessage());
        }
        return "redirect:/users";
    }

    @GetMapping("/subscription")
    public String getSubscriptionByUser(Model model) {
        LoggedUserIdAndRoleResponse loggedUser = userService.getLoggerUser();
        List<Subscription> listPublications = subscriptionService.getSubscriptionForUserId(loggedUser.getId());
        model.addAttribute(ID_LOGIN_USER, loggedUser.getId());
        model.addAttribute(USER_ROLE, loggedUser.getRole());
        model.addAttribute("listPublications", listPublications);
        return "user_subscription";
    }

    @GetMapping("/profile")
    public String getUserProfile(RedirectAttributes redirectAttributes, Model model) {
        try {
            User user = userService.get();
            List<User> listUsers = new ArrayList<>();
            listUsers.add(user);
            model.addAttribute(ID_LOGIN_USER, user.getId());
            model.addAttribute(USER_ROLE, user.getRole().getName());
            model.addAttribute("listUsers", listUsers);
            redirectAttributes.addFlashAttribute(SUCCESS_MESSAGE,
                    "State for Your profile has been changed successfully.");
        } catch (UserNotFoundByIdException e) {
            redirectAttributes.addFlashAttribute(ERROR_MESSAGE, e.getMessage());
        }
        return "user_profile";
    }
}
