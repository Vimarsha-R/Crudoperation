package com.example.Crud.controller;


import com.example.Crud.Entity.User;
import com.example.Crud.advice.Exceptionhandler.*;
import com.example.Crud.dto.UserReq;
import com.example.Crud.dto.Userdto;
import com.example.Crud.repo.UserRepository;
import com.example.Crud.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
public class Usercontroller {
    @Autowired
    private final UserService service;
    private final UserRepository repository;

    public Usercontroller(UserService service, UserRepository repository) {

        this.service = service;
        this.repository = repository;
    }

    @PostMapping("/addusers") //adding
    public ResponseEntity<User> saveUser(@RequestBody @Valid UserReq userReq) throws DuplicationException {

        String name= userReq.getName();
        String email= userReq.getEmail();
        String mobile= userReq.getMobile();

        if(repository.duplicate(name, email, mobile)){
           throw new DuplicationException("duplication is not allowed!!");
        }
        return new ResponseEntity<>(service.save(userReq), HttpStatus.CREATED);
    }

    @GetMapping("/getallusers") //getting all
    public ResponseEntity<List<User>> getallUsers(){
        return ResponseEntity.ok(service.getallUsers());
    }

    @GetMapping("/getuserbyid/{id}") //get by id
    public ResponseEntity<User> getuser(@PathVariable int id) throws UserNotFoundException {
        return ResponseEntity.ok(service.getUser(id));
    }

    @GetMapping("/getAllUserNames") //partial details
    public List<Userdto> getUserNames() {
        List<User> users = repository.findAll();
        List<Userdto> userDtos = new ArrayList<>();
        for (User user : users) {
            userDtos.add(new Userdto(user.getName()));
        }
        return userDtos;
    }

    @GetMapping("/getuserNamesById/{id}") //partial details by id
    public ResponseEntity<Userdto> getById(@PathVariable int id) {
        Optional<User> bookData = repository.findById(id);
        return bookData.map(user -> new ResponseEntity<>(new Userdto(user.getName()), HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PutMapping("/updateuserbyid/{id}") //updating
    public ResponseEntity<User> updateUser(@PathVariable int id, @RequestBody @Valid UserReq userReq) throws UserNotFoundException {
        return ResponseEntity.ok(service.updateUser(id, userReq));
    }

    @DeleteMapping("/deleteuser/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable int id) throws UserNotFoundException {
        service.deleteUser(id);
        return ResponseEntity.ok("User deleted successfully");
    }
}
