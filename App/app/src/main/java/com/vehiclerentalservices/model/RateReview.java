package com.vehiclerentalservices.model;

public class RateReview {

    private int id;
    private String rate;
    private String review;
    User user;
    Vehicle vehicle;

    public RateReview(int id, String rate, String review, User user, Vehicle vehicle) {
        this.id = id;
        this.rate = rate;
        this.review = review;
        this.user = user;
        this.vehicle = vehicle;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public void setVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
    }
}
