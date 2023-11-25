package ru.kata.spring.boot_security.demo.dao;

import ru.kata.spring.boot_security.demo.models.User;

import java.util.List;

public interface UserDao {
    List<User> findAll();

    User findOne(int id);

    void saveUser(User user);

    void update(int id, User updatedUser);

    void deleteUser(int id);

    User findByUserName(String username);

}