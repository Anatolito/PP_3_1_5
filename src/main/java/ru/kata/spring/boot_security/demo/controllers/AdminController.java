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
    String redirect = "redirect:/admin";
    String userInfo = "admin/userInfo";

    @GetMapping
    public String getAdminPage(@AuthenticationPrincipal User user, Model model) {
        model.addAttribute("users", userService.getAllUsers());
        model.addAttribute("user", user);
        return "admin/adminPage";
    }

    @GetMapping(value = "/new")
    public String newUser(@ModelAttribute User user, Model model){
        model.addAttribute("user", user);
        model.addAttribute("roles", roleService.getAllRoles());
        return userInfo;
    }

    @PostMapping
    public String createUser(@ModelAttribute("user") @Valid User user, BindingResult bindingResult,
                             @RequestParam(value = "roles") String[] selectResult) {
        if (bindingResult.hasErrors()) {
            return userInfo;
        }
        for (String s : selectResult) {
            user.setRoles(Collections.singleton(roleService.getRoleByName(s)));
        }
        userService.saveUser(user);
        return redirect;
    }

    @GetMapping(value = "/updateUser")
    public String updateUser(Model model, @RequestParam("userId") Long id) {
        User user = userService.getUser(id);
        model.addAttribute("user", user);
        model.addAttribute("roles",roleService.getAllRoles());
        return userInfo;
    }

    @GetMapping(value = "/deleteUser")
    public String deleteUser(@RequestParam("userId") Long id) {
        userService.deleteUser(id);
        return redirect;
    }
}