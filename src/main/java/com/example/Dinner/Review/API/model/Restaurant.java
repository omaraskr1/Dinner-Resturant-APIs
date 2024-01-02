package com.example.Dinner.Review.API.model;


import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.text.DecimalFormat;

@Entity
@Table(name="RESTAURANTS")
//when there is field with null value, it will be excluded
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Restaurant {

    private  final static DecimalFormat df = new DecimalFormat("###.##");

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

    @Getter
    @Column(name="PEANUT")
    private Float peanut;

    @Getter
    @Column(name="EGG")
    private Float egg;

    @Getter
    @Column(name="DAIRY")
    private Float dairy;

    public void setPeanut(Float peanut) {
        this.peanut = Float.valueOf(Restaurant.df.format(peanut));
    }
    public void setEgg(Float egg) {
        this.egg = Float.valueOf(Restaurant.df.format(egg));
    }

    public void setDaily(Float dairy) {
        this.dairy = Float.valueOf(Restaurant.df.format(dairy));
    }


}
