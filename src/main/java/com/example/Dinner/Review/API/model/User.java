package com.example.Dinner.Review.API.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name="resturant_USERS")
//when there is field with null value, it will be excluded
@JsonInclude(JsonInclude.Include.NON_NULL)
public class User {
    @Id
    @Getter
    @Setter
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Getter
    @Setter
    @Column(name="NAME")
    private String name;

    @Getter
    @Setter
    @Column(name="CITY")
    private String city;

    @Getter
    @Setter
    @Column(name="STATE")
    private String state;

    @Getter
    @Setter
    @Column(name="ZIPCODE")
    private String zipcode;

//    Is the user interested in peanut allergies
    @Getter
    @Setter
    @Column(name="PEANUT")
    private Boolean isPeanutAllergic;

    // Is the user interested in egg allergies
    @Getter
    @Setter
    @Column(name="EGG")
    private Boolean isEggAllergic;

    // Is the user interested in dairy allergies
    @Getter
    @Setter
    @Column(name="DAIRY")
    private Boolean isDairyAllergic;
}
