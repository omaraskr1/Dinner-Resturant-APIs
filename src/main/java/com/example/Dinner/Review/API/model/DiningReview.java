package com.example.Dinner.Review.API.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import wiremock.com.fasterxml.jackson.annotation.JsonInclude;

import java.text.DecimalFormat;

@Entity
@Table(name = "REVIEWS")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class DiningReview {

    private final static DecimalFormat df = new DecimalFormat("###.##");

    @Id
    @Getter
    @Setter
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Getter
    @Setter
    @Column(name = "RESTAURANT_ID")
    private long restaurantId;

    @Getter
    @Setter
    @Column(name = "NAME")
    private String name;

    @Getter
    @Column(name="PEANUT")
    private Float peanutScore;


    @Getter
    @Column(name="EGG")
    private Float eggScore;


    @Getter
    @Column(name="DAIRY")
    private Float dairyScore;


    @Getter
    @Setter
    @Column(name="COMMENT")
    private String comment = "";

    @Getter
    @Setter
    @Column(name="STATUS")
    private Boolean status;

    public void setPeanutScore(Float peanutScore) {
        this.peanutScore = Float.valueOf(DiningReview.df.format(peanutScore));
    }

    public void setEggScore(Float eggScore) {
        this.eggScore = Float.valueOf(DiningReview.df.format(eggScore));
    }

    public void setDairyScore(Float dairyScore) {
        this.dairyScore = Float.valueOf(DiningReview.df.format(dairyScore));;
    }


}
