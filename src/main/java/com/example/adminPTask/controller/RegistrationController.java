package com.example.adminPTask.controller;


import com.example.adminPTask.domain.UserAccount;
import com.example.adminPTask.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.util.Map;

@Controller
public class RegistrationController {
    @Autowired
    private UserService userService;

    @GetMapping("/registration")
    public String registration() {
        return "registration";
    }

    @PostMapping("/registration")
    public String addUser(@Valid UserAccount user, BindingResult bindingResult, Model model) {
        if (user.getPassword() != null && !user.getPassword().equals(user.getPassword2())) {
            model.addAttribute("passwordError", "Passwords are different!");

            return "registration";
        }
        if (!user.getPassword().matches("(.*)[a-zA-Z]+(.*)")
                || !user.getPassword().matches("(.*)[0-9]+(.*)")
                || !user.getPassword().matches("^[a-zA-Z0-9]+$")) {
            model.addAttribute("passwordError", "Password can only consist of latin characters and numbers.Password must contain at least 1 character and 1 digit");
            return "registration";
        }

        if (bindingResult.hasErrors()) {
            Map<String, String> errors = ControllerUtils.getErrors(bindingResult);

            model.mergeAttributes(errors);

            return "registration";
        }

        if (!userService.addUser(user)) {
            model.addAttribute("usernameError", "User exists!");
            return "registration";
        }

        return "redirect:/login";
    }
}

