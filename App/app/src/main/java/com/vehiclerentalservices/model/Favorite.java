package com.vehiclerentalservices.model;

public class Favorite {

    private int id;
    User user;
    Vehicle vehicle;

    public Favorite(int id, User user, Vehicle vehicle) {
        this.id = id;
        this.user = user;
        this.vehicle = vehicle;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
