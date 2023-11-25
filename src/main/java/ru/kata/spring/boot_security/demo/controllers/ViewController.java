package ru.kata.spring.boot_security.demo.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ru.kata.spring.boot_security.demo.models.User;

@Controller
public class ViewController {

    @GetMapping("/admin")
    public String showAllUser(Model model) {
        model.addAttribute("newUser", new User());
        return "adminPage";
    }

    @GetMapping("/user")
    public String showOneUser() {
        return "userPage";
    }

    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }
}
