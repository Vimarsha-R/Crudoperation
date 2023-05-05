package com.example.Crud.service;

import com.example.Crud.Entity.User;
import com.example.Crud.advice.Exceptionhandler;
import com.example.Crud.dto.UserReq;
import com.example.Crud.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    public UserRepository repository;

    public User save(UserReq userReq) {
        User user = User.build(0, userReq.getName(), userReq.getEmail(), userReq.getMobile(), userReq.getAge());
        return repository.save(user);
    }

    public List<User> getallUsers() {
        return repository.findAll();
    }

    public User getUser(int id) throws Exceptionhandler.UserNotFoundException {
        Optional<User> user = repository.findById(id);
        if (user.isEmpty()) {
            throw new Exceptionhandler.UserNotFoundException("user not found with id: " + id);
        }
        return user.get();
    }

    public User updateUser(int id, UserReq userReq) throws Exceptionhandler.UserNotFoundException {

        Optional<User> optionalUser = repository.findById(id);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            user.setName(userReq.getName());
            user.setEmail(userReq.getEmail());
            user.setMobile(userReq.getMobile());
            user.setAge(userReq.getAge());
            return repository.save(user);
        } else {
            throw new Exceptionhandler.UserNotFoundException("user not found with id: "+ id);
        }
    }

    public void deleteUser(int id) throws Exceptionhandler.UserNotFoundException {
        Optional<User> optionalUser = repository.findById(id);
        if (optionalUser.isPresent()) {
            repository.deleteById(id);
        } else {
            throw new Exceptionhandler.UserNotFoundException("user not found with id: "+ id);
        }
    }
}
