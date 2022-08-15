package org.cegielka.periodicals.controller;

import lombok.AllArgsConstructor;
import org.cegielka.periodicals.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.HttpRequestHandler;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.Locale;

@Controller
@AllArgsConstructor
public class MainController {

    UserService userService;

    @GetMapping("")
    public String homePage(Model model, HttpServletRequest request) {
        Long numberIdForLoginUser = userService.getIdUserWhichIsLogin();
        String roleForLoginUser = userService.getUserRoleWhichIsLogin();
        model.addAttribute("idLoginUser", numberIdForLoginUser);
        model.addAttribute("userRole", roleForLoginUser);
        model.addAttribute("pageTitle","Welcome to periodicals page");

        Locale locale=request.getLocale();
        String countryCode=locale.getCountry();
        String countryName=locale.getDisplayCountry();
        String langCode=locale.getLanguage();
        String langName=locale.getDisplayLanguage();
        System.out.println(countryCode+  " : " + countryName);
        System.out.println(langCode+  " : " + langName);

        return "index";
    }

}