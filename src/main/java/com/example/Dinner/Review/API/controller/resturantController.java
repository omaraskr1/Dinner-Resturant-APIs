package com.example.Dinner.Review.API.controller;

import com.example.Dinner.Review.API.exceptions.QueryNotSupportedException;
import com.example.Dinner.Review.API.model.Restaurant;
import com.example.Dinner.Review.API.repository.RestuarntRepositery;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@RestController
@RequestMapping("/restaurants")
public class resturantController {
    private final RestuarntRepositery resturantRepositery;

    public resturantController(final RestuarntRepositery resturantrepositery) {
        this.resturantRepositery = resturantrepositery;
    }

    @GetMapping
    public ResponseEntity<Iterable<Restaurant>> getAllResturants(){
        return new ResponseEntity<Iterable<Restaurant>>(this.resturantRepositery.findAll(), HttpStatus.OK);
    }

    @GetMapping(path="/{restaurantId}")
    public ResponseEntity<Restaurant> getRestaurant(@PathVariable(name="restaurantId") Long id) {
        Optional<Restaurant> restaurantOptional = this.resturantRepositery.findById(id);

        if (restaurantOptional.isEmpty()) return new ResponseEntity<Restaurant>(HttpStatus.NOT_FOUND);

        return new ResponseEntity<Restaurant>(restaurantOptional.get(), HttpStatus.OK);
    }

    @PostMapping(path="/new")
    public ResponseEntity<Restaurant> addNewRestaurant(@RequestBody Restaurant newRestruants) {
        if(Objects.isNull((newRestruants.getName()))||
                Objects.isNull((newRestruants.getZipcode()))||
                Objects.isNull((newRestruants.getCity()))||
                Objects.isNull((newRestruants.getState()))
                )

        {
        return new ResponseEntity<Restaurant>(HttpStatus.BAD_REQUEST);
        }
        if(this.resturantRepositery.existsByNameAndZipcode(newRestruants.getName(),newRestruants.getZipcode()))
        {
            return new ResponseEntity<Restaurant>(HttpStatus.CONFLICT);

        }
        return new ResponseEntity<Restaurant>(this.resturantRepositery.save(newRestruants),HttpStatus.CREATED);


    }

    @GetMapping("/search")
    public Object searchRestaurants(
            @RequestParam(name="zipcode",required = false) String zipcode,
            @RequestParam(name="allergy",required = false) String allergy
    ) throws QueryNotSupportedException
    {
        if(Objects.nonNull((zipcode))&&Objects.nonNull((allergy))){
            return new ResponseEntity<List<Restaurant>>(
                    this.resturantRepositery.findByZipcodeAndAllergyDesc(zipcode,allergy),
                    HttpStatus.OK);
        }
        else if(Objects.nonNull((zipcode))){
            return new ResponseEntity<List<Restaurant>>(
                    this.resturantRepositery.findByZipcode(zipcode),
                    HttpStatus.OK );

        }
        else if(Objects.nonNull((allergy))){
            return new ResponseEntity<List<Restaurant>>(
                    this.resturantRepositery.findByAllergyDesc(allergy),
                    HttpStatus.OK);
        }

        return new QueryNotSupportedException("Please provide at least one of them zipcode and allergy");

    }





}
