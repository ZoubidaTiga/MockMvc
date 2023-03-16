package com.example.restprojet.service;

import com.example.restprojet.exceptions.UserNotFoundException;
import com.example.restprojet.model.User;
import com.example.restprojet.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ServiceUserImpl implements IServiceUser {

    @Autowired
    private UserRepository repository;

    @Override
    public List<User> getAllUsers() {
        return repository.findAll();
    }

    @Override
    public User getUserById(Long id) throws UserNotFoundException {
        return repository.findById(id).orElseThrow(()->new UserNotFoundException ("user not found"));
    }

    @Override
    public void deleteUser(Long id) {
        repository.deleteById(id);
    }

    @Override
    public User createUser(User user) {
        return repository.save(user);
    }

    @Override
    public User update(User user){
        return repository.save(user);
    }

}
