package ru.kata.spring.boot_security.demo.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.models.Role;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.services.RoleService;
import ru.kata.spring.boot_security.demo.services.UserService;

import javax.annotation.PostConstruct;
import java.util.HashSet;
import java.util.Set;

@Component
public class Init {
    private final UserService userService;
    private final RoleService roleService;

    @Autowired
    public Init(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @Transactional
    @PostConstruct
    public void initializeDataBase(){
        roleService.save(new Role("ROLE_ADMIN"));
        roleService.save(new Role("ROLE_USER"));

        Set<Role> adminRole = new HashSet<>();
        Set<Role> userRole = new HashSet<>();
        Set<Role> allRoles = new HashSet<>();

        adminRole.add(roleService.showUserById(1));
        userRole.add(roleService.showUserById(2));
        allRoles.add(roleService.showUserById(1));
        allRoles.add(roleService.showUserById(2));

        userService.saveUser(new User("Anatoly", "Zakharov",41,"admin@mail.ru","admin",adminRole));
        userService.saveUser(new User("Ivan", "Ivanov", 25, "user@mail.ru", "user", userRole));
        userService.saveUser(new User("Petr", "Petrov",25,"adminuser@mail.ru","adminuser",allRoles));
    }
}

