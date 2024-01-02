package com.example.Dinner.Review.API.repository;

import com.example.Dinner.Review.API.model.Restaurant;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface RestuarntRepositery extends CrudRepository<Restaurant, Long> {
    Optional<Restaurant> findById(Long id);

    boolean existsByNameAndZipcode(String name, String zipcode);


    default List<Restaurant> findByZipcodeAndAllergyDesc(String zipcode, String allergy) {
        return switch (allergy) {
            case "peanut" -> findByZipcodeAndPeanutAllergyDesc(zipcode);
            case "egg" -> findByZipcodeAndEggAllergyDesc(zipcode);
            case "dairy" -> findByZipcodeAndDairyAllergyDesc(zipcode);
            default -> List.of();
        };
    }

    @Query(value="SELECT r FROM Restaurant r WHERE r.zipcode=?1 AND r.dairy IS NOT NULL" +
            " ORDER BY r.dairy DESC")
    List<Restaurant> findByZipcodeAndDairyAllergyDesc(String zipcode);

    @Query(
        value = "select r from Restaurant r where zipcode=?1 and egg is not null order by egg desc"
    )
    List<Restaurant> findByZipcodeAndEggAllergyDesc(String zipcode);

    @Query(
            value = "select r from Restaurant r where r.zipcode = ?1 and r.peanut is not null order by r.peanut DESC"
    )
    List<Restaurant> findByZipcodeAndPeanutAllergyDesc(String zipcode);

    List<Restaurant> findByZipcode(String zipcode);

//    List<Restaurant> findByAllergy(String allergy);

    default List<Restaurant> findByAllergyDesc(String allergy){
        return switch (allergy) {
            case "peanut" -> findByPeanutAllergyDesc();
            case "egg" -> findByEggAllergyDesc();
            case "dairy" -> findByDairyAllergyDesc();
            default -> List.of();
        };
    }

    @Query(
            value = "select r from Restaurant r where r.dairy is not null order by r.dairy DESC"
    )
    List<Restaurant> findByDairyAllergyDesc();

    @Query(
            value = "select r from Restaurant r where r.egg is not null order by r.egg DESC"
    )
    List<Restaurant> findByEggAllergyDesc();

    @Query(
            value = "select r from Restaurant r where r.peanut is not null order by r.peanut DESC"
    )
    List<Restaurant> findByPeanutAllergyDesc();
}
