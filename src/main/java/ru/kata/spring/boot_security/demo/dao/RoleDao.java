package ru.kata.spring.boot_security.demo.dao;

import ru.kata.spring.boot_security.demo.models.Role;

import java.util.List;

public interface RoleDao {
    List<Role> getAllUsers();

    void save(Role role);

    void deleteById(int id);

    Role showUserById(int id);

    Role findRoleByRole(String role);

}