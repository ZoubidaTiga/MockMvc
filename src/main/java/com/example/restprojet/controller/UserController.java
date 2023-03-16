package com.example.restprojet.controller;


import com.example.restprojet.exceptions.UserNotFoundException;
import com.example.restprojet.model.User;
import com.example.restprojet.service.IServiceUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Validated
@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private IServiceUser serviceUser;

    @GetMapping
    public ResponseEntity<List<User>> getAllUsers(){
        List<User> users = serviceUser.getAllUsers();
        if (users.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @PostMapping
    User addUser(@RequestBody User newUser) {
        return serviceUser.createUser (newUser);
    }

    @GetMapping("/{id}")
    ResponseEntity<User> getById(@PathVariable Long id) throws UserNotFoundException {
        return new ResponseEntity (serviceUser.getUserById (id), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    void deleteUser(@PathVariable Long id) {
        serviceUser.deleteUser(id);
    }

    @PutMapping
    User updateUser(@RequestBody User newUser) {
        return serviceUser.update( newUser);
    }




}
