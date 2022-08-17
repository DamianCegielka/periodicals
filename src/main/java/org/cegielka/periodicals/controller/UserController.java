package org.cegielka.periodicals.controller;

import lombok.AllArgsConstructor;
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

    private final UserService userService;
    private final SubscriptionService subscriptionService;
    private static final String MESSAGE = "MESSAGE";
    private static final String ID_LOGIN_USER = "idLoginUser";
    private static final String USER_ROLE = "userRole";

    @GetMapping("")
    public String getUserList(Model model) {
        List<User> listUsers = userService.listAll();
        Long numberIdForLoginUser = userService.getIdUserWhichIsLogin();
        String roleForLoginUser = userService.getUserRoleWhichIsLogin();
        model.addAttribute(ID_LOGIN_USER, numberIdForLoginUser);
        model.addAttribute(USER_ROLE, roleForLoginUser);
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
            System.out.println(user);
            userService.register(user);
            redirectAttributes.addFlashAttribute(MESSAGE, "The user has been saved successfully.");
            return "redirect:/users";

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @GetMapping("/edit/{id}")
    public String reverseActiveState(@PathVariable("id") Long id, RedirectAttributes redirectAttributes, Model model) {
        try {
            Long numberIdForLoginUser = userService.getIdUserWhichIsLogin();
            String roleForLoginUser = userService.getUserRoleWhichIsLogin();
            model.addAttribute(ID_LOGIN_USER, numberIdForLoginUser);
            model.addAttribute(USER_ROLE, roleForLoginUser);
            userService.reverseState(id);
            redirectAttributes.addFlashAttribute(MESSAGE, "State for id " + id + " has been changed successfully.");
        } catch (UserNotFoundByIdException e) {
            redirectAttributes.addFlashAttribute(MESSAGE, e.getMessage());
        }
        return "redirect:/users";
    }

    @GetMapping("/subscription")
    public String getSubscriptionByUser(Model model) {
        List<Subscription> listPublications = subscriptionService
                .getSubscriptionForUserId(userService.getIdUserWhichIsLogin());
        Long numberIdForLoginUser = userService.getIdUserWhichIsLogin();
        String roleForLoginUser = userService.getUserRoleWhichIsLogin();
        model.addAttribute(ID_LOGIN_USER, numberIdForLoginUser);
        model.addAttribute(USER_ROLE, roleForLoginUser);
        model.addAttribute("listPublications", listPublications);
        return "user_subscription";
    }

    @GetMapping("/profile")
    public String getUserProfile(RedirectAttributes redirectAttributes, Model model) {
        try {
            Long numberIdForLoginUser = userService.getIdUserWhichIsLogin();
            String roleForLoginUser = userService.getUserRoleWhichIsLogin();
            User user = userService.get(numberIdForLoginUser);
            List<User> listUsers = new ArrayList<>();
            listUsers.add(user);
            model.addAttribute(ID_LOGIN_USER, numberIdForLoginUser);
            model.addAttribute(USER_ROLE, roleForLoginUser);
            model.addAttribute("listUsers", listUsers);
            redirectAttributes.addFlashAttribute(MESSAGE, "State for Your profile has been changed successfully.");
        } catch (UserNotFoundByIdException e) {
            redirectAttributes.addFlashAttribute(MESSAGE, e.getMessage());
        }
        return "user_profile";
    }
}
