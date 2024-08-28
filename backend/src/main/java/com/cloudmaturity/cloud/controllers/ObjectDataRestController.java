package com.cloudmaturity.cloud.controllers;

import com.cloudmaturity.cloud.mongorepositories.UserRepository;
import com.cloudmaturity.cloud.objects.Category;
import com.cloudmaturity.cloud.mongorepositories.CategoryRepository;
import com.cloudmaturity.cloud.objects.User;
import com.cloudmaturity.cloud.objects.UserDTO;
import com.cloudmaturity.cloud.objects.UserSubmission;
import com.mongodb.MongoException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@CrossOrigin
@RestController
@RequestMapping("/db")
public class ObjectDataRestController {
    @Autowired
    UserRepository userRepository;

    @Autowired
    CategoryRepository categoryRepository;

    @GetMapping(value="/getCategories/")
    public ResponseEntity<Collection<Category>> getAllCategories() {
        Collection<Category> result = categoryRepository.findAll();
        if(result.isEmpty()){return ResponseEntity.noContent().build();}
        return ResponseEntity.ok().body(result);
    }

    @GetMapping(value="/getUserByEmail/{email}", produces={"application/json"})
    public ResponseEntity<User> getUserByEmail(@PathVariable String email) {
        var user = userRepository.getUserByEmail(email);
        if (user == null) {
            return ResponseEntity.notFound().build();
        }
        else {
            return ResponseEntity.ok().body(user);
        }
    }

    @GetMapping(value="/getUserById/{id}", produces={"application/json"})
    public ResponseEntity<User> getUserById(@PathVariable String id) {
        var user = userRepository.getUserById(id);
        if (user == null) {return ResponseEntity.noContent().build();}
        
        return ResponseEntity.ok().body(user);
    }

    @GetMapping(value="/getUserAnswers/{id}", produces={"application/json"})
    public ResponseEntity<List<UserSubmission>> getUserAnswers(@PathVariable String id) {
        var user = userRepository.getUserById(id);
        if (user == null) { return ResponseEntity.noContent().build();}

        return ResponseEntity.ok().body(user.getUserSubmissions());
    }

    @PostMapping(value = "/createUser/",
            consumes = {"application/json"},
            produces = {"application/json"})
    public ResponseEntity<User> createUser(@RequestBody UserDTO userDTO) {
        try{
            var user = new User(userDTO.getEmail());
            user = userRepository.save(user);
            return ResponseEntity.ok().body(user);
        } catch(MongoException ex){
            System.out.println(ex.getMessage());
        }
        return ResponseEntity.noContent().build();
    }

    @PostMapping(value="/submitUserAnswers/{id}")
    public ResponseEntity<List<UserSubmission>> postUserSubmission(@PathVariable String id, @RequestBody UserSubmission submission) {
        var user = userRepository.getUserById(id);
        if(user == null){return ResponseEntity.noContent().build();}

        user.addAnswer(submission);
        userRepository.save(user);
        return ResponseEntity.ok().body(user.getUserSubmissions());
    }

}
