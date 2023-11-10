package ru.kata.spring.boot_security.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.services.RoleService;
import ru.kata.spring.boot_security.demo.services.UserService;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {
    private UserService userService;
    private RoleService roleService;

    @Autowired
    public AdminController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }
    String redirect = "redirect:/admin/";


    @GetMapping("/")
    public String getAdminPage(@AuthenticationPrincipal User user, Model model) {
        model.addAttribute("users", userService.getAllUsers());
        model.addAttribute("user", user);
        model.addAttribute("roles", roleService.getAllRoles());
        return "admin/adminPage";
    }

    @GetMapping("/add")
    public String newUserPage(Model model) {
        model.addAttribute("user", new User());
        model.addAttribute("roles", roleService.getAllRoles());
        return "admin/newUser";
    }

    @PostMapping("/new")
    public String createUser(@ModelAttribute("user") @Valid User user, BindingResult bindingResult,
                             @RequestParam(value = "roles") String[] selectResult) {
        if (bindingResult.hasErrors()) {
            return "admin/newUser";
        }
        for (String s : selectResult) {
            user.setRoles(Collections.singleton(roleService.getRoleByName(s)));
        }
        userService.saveUser(user);
        return redirect;
    }

    @PatchMapping(value = "/{id}")
    public String updateUser(@ModelAttribute("user") @Valid User user, BindingResult bindingResult,
                             @RequestParam(value = "roles") String[] selectResult) {
        if (!bindingResult.hasErrors()) {
            for (String s : selectResult) {
                user.setRoles(Collections.singleton(roleService.getRoleByName(s)));
            }
            userService.updateUser(user);
        }
        return redirect;
    }

    @DeleteMapping(value = "/{id}")
    public String deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return redirect;
    }
}