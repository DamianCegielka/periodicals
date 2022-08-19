package org.cegielka.periodicals.controller;

import lombok.AllArgsConstructor;
import org.cegielka.periodicals.dto.LoggedUserIdAndRoleResponse;
import org.cegielka.periodicals.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@AllArgsConstructor
public class MainController {

    private static final String ERROR_MESSAGE = "error";
    private final UserService userService;

    @GetMapping("")
    public String homePage(Model model) {
        LoggedUserIdAndRoleResponse loggedUser = userService.getLoggerUser();
        model.addAttribute("idLoginUser", loggedUser.getId());
        model.addAttribute("userRole", loggedUser.getRole());
        model.addAttribute("pageTitle", "Welcome to periodicals page");
        return "index";
    }

    @GetMapping("/login")
    public String loginUser(Model model) {
            return "login_form";
    }

    @GetMapping("/logout")
    public String logoutUser(Model model) {
        return "logout_form";
    }
}