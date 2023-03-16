package com.example.restprojet.service;

import com.example.restprojet.exceptions.UserNotFoundException;
import com.example.restprojet.model.User;

import java.util.List;

public interface IServiceUser {

    List<User> getAllUsers();
    User getUserById(Long id) throws UserNotFoundException;
    void deleteUser(Long id);
    User createUser(User user);
    User update(User user);
}
