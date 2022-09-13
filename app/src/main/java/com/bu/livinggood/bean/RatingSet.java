package com.bu.livinggood.bean;

import java.io.Serializable;

public class RatingSet implements Serializable {
    private PropertyResponse apartment;
    private double overall_score;
    private double security_score;
    private double foods_score;
    private double transportation_score;
    private double stores_score;

    private int securityNum = 0;
    private int foodNum = 0;
    private int transitNum = 0;
    private int storeNum = 0;

    public RatingSet(PropertyResponse apartment, double security_score, double foods_score, double transportation_score, double stores_score, double overall_score) {
        this.apartment = apartment;
        this.security_score = security_score;
        this.foods_score = foods_score;
        this.transportation_score = transportation_score;
        this.stores_score = stores_score;
        this.overall_score = overall_score;
    }

    public PropertyResponse getApartment() {
        return apartment;
    }

    public double getOverallScore() {
        return overall_score;
    }

    public double getSecurity_score() {
        return security_score;
    }

    public double getFoods_score() {
        return foods_score;
    }

    public double getTransportation_score() {
        return transportation_score;
    }

    public double getStores_score() {
        return stores_score;
    }

    public int getSecurityNum() {
        return securityNum;
    }

    public void setSecurityNum(int securityNum) {
        this.securityNum = securityNum;
    }

    public int getFoodNum() {
        return foodNum;
    }

    public void setFoodNum(int foodNum) {
        this.foodNum = foodNum;
    }

    public int getTransitNum() {
        return transitNum;
    }

    public void setTransitNum(int transitNum) {
        this.transitNum = transitNum;
    }

    public int getStoreNum() {
        return storeNum;
    }

    public void setStoreNum(int storeNum) {
        this.storeNum = storeNum;
    }
}
