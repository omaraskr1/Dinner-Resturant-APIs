package com.example.Dinner.Review.API.controller;

import com.example.Dinner.Review.API.model.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.Dinner.Review.API.repository.userRepositery;

import java.util.Objects;

@RestController
@RequestMapping("/users")
public class userController {

    private final userRepositery userRepositery;

    public userController(com.example.Dinner.Review.API.repository.userRepositery userRepositery) {
        this.userRepositery = userRepositery;
    }

    @GetMapping
    public ResponseEntity<Iterable<User>> getAllUsers() {
        ResponseEntity<Iterable<User>> iterableResponseEntity = new ResponseEntity<>(this.userRepositery.findAll(),
                HttpStatus.OK);
        return iterableResponseEntity;


    }
    @GetMapping("/{name}")
    public ResponseEntity<User> getUserByName(@PathVariable("name") String name) {
        var userOptional = this.userRepositery.findByName(name);
        if(!userOptional.isPresent()){
            return new ResponseEntity<User>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<User>(userOptional.get(),HttpStatus.OK);

    }

    @PostMapping("/new/signup")
    public ResponseEntity<User> createUser(@RequestBody User user) {
        //if json data has missing data
        if(
                Objects.isNull(user.getName()) ||
                Objects.isNull(user.getCity()) ||
                Objects.isNull(user.getState()) ||
                Objects.isNull(user.getZipcode()) ||
                Objects.isNull(user.getIsPeanutAllergic()) ||
                Objects.isNull(user.getIsEggAllergic())
        ){
            return new ResponseEntity<User>(HttpStatus.BAD_REQUEST);
        }
        //if there is a user with that name
        if(this.userRepositery.existsUserByName(user.getName())){
            return new ResponseEntity<User>(HttpStatus.CONFLICT);
        }
        return new ResponseEntity<User>(this.userRepositery.save(user),HttpStatus.CREATED);
    }


}
