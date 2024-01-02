package com.example.Dinner.Review.API.repository;

import com.example.Dinner.Review.API.model.DiningReview;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface DiningReviewRepository extends CrudRepository<DiningReview, Long> {


    boolean existsDinigReviewByRestaurantIdAndName(Long restaurantId, String name);

    List<DiningReview> findByStatus(boolean b);

    List<DiningReview> findByRestaurantIdAndStatus(Long id, boolean b);

    boolean existsDiningReviewByNameAndRestaurantId(String name, long restaurantId);
}
