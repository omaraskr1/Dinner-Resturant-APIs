package com.example.Dinner.Review.API.controller;

import com.example.Dinner.Review.API.model.DiningReview;
import com.example.Dinner.Review.API.repository.DiningReviewRepository;
import com.example.Dinner.Review.API.repository.RestuarntRepositery;
import com.example.Dinner.Review.API.repository.UserRepositery;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;
import java.util.Optional;

@RestController
@RequestMapping("/reviews")
public class DiningReviewController {

    private final DiningReviewRepository diningReviewRepository;
    private final RestuarntRepositery resturantRepository;
    private final UserRepositery userRepositery;

    public DiningReviewController(
            DiningReviewRepository diningReviewRepository,
            RestuarntRepositery resturantRepository,
            UserRepositery userRepositery) {
        this.diningReviewRepository = diningReviewRepository;
        this.resturantRepository = resturantRepository;
        this.userRepositery = userRepositery;
    }
    @GetMapping
    public ResponseEntity<Iterable<DiningReview>> getAllDiningReviews(){
        return new ResponseEntity<Iterable<DiningReview>>(this.diningReviewRepository.findAll(), HttpStatus.OK);
    }

    @GetMapping("/{reviewsId}")
    public ResponseEntity<DiningReview> getDiningReviewByID(
            @PathVariable("reviewsId") long reviewId
    ){
        Optional<DiningReview> optionalReview=this.diningReviewRepository.findById(reviewId);
        if(optionalReview.isEmpty()){
            return new ResponseEntity<DiningReview>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<DiningReview>(optionalReview.get(), HttpStatus.OK);
    }

    @PostMapping("/new")
    public ResponseEntity<DiningReview> createNewDiningReview(
            @RequestBody DiningReview newDiningReview

    ){
        //if any field is null
        if(
                Objects.isNull(newDiningReview.getName())||
                Objects.isNull(newDiningReview.getRestaurantId())||
                Objects.isNull(newDiningReview.getPeanutScore())||
                Objects.isNull(newDiningReview.getEggScore())||
                Objects.isNull(newDiningReview.getDairyScore())
//                ||Objects.isNull(newDiningReview.getStatus())
                ||Objects.isNull(newDiningReview.getId())

        ){
            return new ResponseEntity<DiningReview>(HttpStatus.BAD_REQUEST);
        }
        //if the resturant or user doesn't exist
        if(
                !this.resturantRepository.existsById(
                        newDiningReview.getRestaurantId()
                )
                        ||
                !this.userRepositery.existsUserByName(
                        newDiningReview.getName()
                )
        )   {
            return new ResponseEntity<DiningReview>(HttpStatus.NOT_FOUND);
        }
        //does the user review this resturant before?
        if(
                this.diningReviewRepository.existsDiningReviewByNameAndRestaurantId(
                        newDiningReview.getName(),
                        newDiningReview.getRestaurantId()

                )

        ){
            return new ResponseEntity<DiningReview>(HttpStatus.CONFLICT);
        }
        newDiningReview.setStatus(false);
        return new ResponseEntity<DiningReview>(this.diningReviewRepository.save(newDiningReview),HttpStatus.CREATED); //return the new review (http)
    }


}
