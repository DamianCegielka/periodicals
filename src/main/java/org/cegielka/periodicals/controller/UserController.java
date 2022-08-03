package org.cegielka.periodicals.controller;

import org.cegielka.periodicals.dto.UserRegistrationRequest;
import org.cegielka.periodicals.entity.User;
import org.cegielka.periodicals.service.exception.UserNotFoundByIdException;
import org.cegielka.periodicals.service.impl.RegistrationServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
public class UserController {

    @Autowired
    private RegistrationServiceImpl userService;

    @GetMapping("/users")
    public String showUserList(Model model) {
        List<User> listUsers = userService.listAll();
        model.addAttribute("listUsers", listUsers);
        return "users";
    }

    @GetMapping("/users/new")
    public String showNewForm(Model model){
        model.addAttribute("user",new UserRegistrationRequest());
        model.addAttribute("pageTitle","Add New User");
        return "register_form";
    }

    @GetMapping("/register")
    public String registerNewUser(Model model){
        model.addAttribute("user",new UserRegistrationRequest());
        model.addAttribute("pageTitle","Register");
        return "register_form";
    }

    @PostMapping("/users/save")
    public String saveUser(UserRegistrationRequest user, RedirectAttributes redirectAttributes){
        userService.register(user);
        redirectAttributes.addFlashAttribute("message","The user has been saved successfully.");
        return "redirect:/users";
    }

    @GetMapping("/users/edit/{id}")
    public String showEditForm(@PathVariable("id") Long id, Model model,RedirectAttributes redirectAttributes){
       try{
          User user= userService.get(id);
          model.addAttribute("user",user);
           model.addAttribute("pageTitle","Edit User (ID: "+ id+")");
           return "update_user_state";
       }
        catch (UserNotFoundByIdException e){
           redirectAttributes.addFlashAttribute("message",e.getMessage());
           return "redirect:/users";
        }
    }

    @GetMapping("/users/delete/{id}")
    public String deleteUser(@PathVariable("id") Long id,RedirectAttributes redirectAttributes){
        try{
            userService.delete(id);
        }
        catch (UserNotFoundByIdException e){
            redirectAttributes.addFlashAttribute("message",e.getMessage());
        }
        return "redirect:/users";
    }

}
