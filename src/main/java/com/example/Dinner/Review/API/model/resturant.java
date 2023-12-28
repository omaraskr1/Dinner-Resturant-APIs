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
public class resturant {

    private  final static DecimalFormat df = new DecimalFormat("###.00");

    @Id
    @Getter
    @Setter
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name="NAME")
    @Getter
    @Setter
    private String name;

    @Column(name="CITY")
    @Getter
    @Setter
    private String city;

    @Column(name="STATE")
    @Getter
    @Setter
    private String state;

    @Column(name="ZIPCODE")
    @Getter
    @Setter
    private String zipcode;

    @Column(name="PEANUT")
    @Getter
    private Float peanut;

    @Column(name="EGG")
    @Getter
    private Float egg;

    @Column(name="DAILY")
    @Getter
    private Float daily;

    public void setPeanut(Float peanut) {
        this.peanut = Float.valueOf(resturant.df.format(peanut));
    }
    public void setEgg(Float egg) {
        this.egg = Float.valueOf(resturant.df.format(egg));
    }

    public void setDaily(Float daily) {
        this.daily = Float.valueOf(resturant.df.format(daily));
    }


}
