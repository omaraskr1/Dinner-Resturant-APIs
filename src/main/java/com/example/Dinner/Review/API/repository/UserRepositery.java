package com.example.Dinner.Review.API.repository;

import com.example.Dinner.Review.API.model.User;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UserRepositery extends CrudRepository<User,Long> {
    Optional<User> findByName(String name);
    Boolean existsUserByName(String name);

}

