package org.cegielka.periodicals.controller;

import org.cegielka.periodicals.dto.UserRegistrationRequest;
import org.cegielka.periodicals.entity.User;
import org.cegielka.periodicals.service.UserService;
import org.cegielka.periodicals.service.exception.UserNotFoundByIdException;
import org.cegielka.periodicals.service.impl.UserServiceImpl;
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
public class UserController {

    private UserService userService;

    public UserController(UserServiceImpl userService) {
        this.userService = userService;
    }

    @GetMapping(" ")
    public String showUserList(Model model) {
        List<User> listUsers = userService.listAll();
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
    public String changeActiveState(@PathVariable("id") Long id, RedirectAttributes redirectAttributes) {
        try {
            userService.updateState(id);
            redirectAttributes.addFlashAttribute("message", "State for id " + id + " has been changed successfully.");
        } catch (UserNotFoundByIdException e) {
            redirectAttributes.addFlashAttribute("message", e.getMessage());
        }
        return "redirect:/users";
    }
}
