package com.example.Dinner.Review.API.controller;

import com.example.Dinner.Review.API.model.DiningReview;
import com.example.Dinner.Review.API.model.Restaurant;
import com.example.Dinner.Review.API.repository.DiningReviewRepository;
import com.example.Dinner.Review.API.repository.RestuarntRepositery;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/admin")
public class AdminController {
    private final DiningReviewRepository diningReviewRepository;
    private final RestuarntRepositery resturantRepository;

    public AdminController(DiningReviewRepository diningReviewRepository, RestuarntRepositery resturantRepository) {
        this.diningReviewRepository = diningReviewRepository;
        this.resturantRepository = resturantRepository;
    }

    @GetMapping("/pindingReviews")
    public ResponseEntity<List<DiningReview>> GetAllReviews() {
        return new ResponseEntity<List<DiningReview>>(this.diningReviewRepository.findByStatus(false), HttpStatus.OK);

    }
    @PutMapping("/pindingReviews/{reviewId}")
    public ResponseEntity<DiningReview> updateReviewStatus(
            @PathVariable("reviewId") long reviewId,
            @RequestParam("status") Boolean status
            )
    {
        Optional<DiningReview> optionalReview=this.diningReviewRepository.findById(reviewId);
        if (optionalReview.isEmpty()){
            return new ResponseEntity<DiningReview>(HttpStatus.NOT_FOUND);
        }
        DiningReview review=optionalReview.get();
        review.setStatus(status);
        review=this.diningReviewRepository.save(review);
        if(status){
            this.updateRestaurantAllergyScores(review);
        }
        return new ResponseEntity<DiningReview>(review,HttpStatus.OK);

    }

    private void updateRestaurantAllergyScores(DiningReview review) {
        Optional<Restaurant> optionalRestaurant=this.resturantRepository.findById(review.getRestaurantId());
        //check if the opject exists else throw exception
        Restaurant resturant=optionalRestaurant.orElseThrow();

        //get approved reviews
        List<DiningReview> diningReview=diningReviewRepository.findByRestaurantIdAndStatus(resturant.getId(),true);
        //calculate allergy score
        //using parallelStream() to enabling processing elements concurrently for potential performance benefits
        //.map(DiningReview::getPeanutScore) applies the getPeanutScore() method to each DiningReview
        //.reduce(0f, Float::sum) reduces the stream of scores into a single value by summing them up.
        // It starts with an initial value of 0f (a float zero) and uses the Float::sum method to accumulate the scores.

        Float peanutAllergyScore = diningReview.parallelStream().map(DiningReview::getPeanutScore).reduce(0f, Float::sum)/diningReview.size();
        Float eggAllergyScore =diningReview.parallelStream().map(DiningReview::getEggScore).reduce(0f, Float::sum)/diningReview.size();
        Float dairyAllergyScore =diningReview.parallelStream().map(DiningReview::getDairyScore).reduce(0f, Float::sum)/diningReview.size();

        resturant.setPeanut(peanutAllergyScore);
        resturant.setEgg(eggAllergyScore);
        resturant.setDaily(dairyAllergyScore);

        this.resturantRepository.save(resturant);

    }
}
