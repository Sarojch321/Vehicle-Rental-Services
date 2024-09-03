package com.vehiclerentalservices.model;

public class Booking {

    private int id;
    private String dateFrom;
    private String dateTo;
    private String totalAmount;
    private String driverNeed;
    private int flag;
    User user;
    Vehicle vehicle;
    Location PickupLocation;
    Location DropoffLocation;

    public Booking(int id, String dateFrom, String dateTo, String totalAmount, String driverNeed, int flag, User user, Vehicle vehicle, Location pickupLocation, Location dropoffLocation) {
        this.id = id;
        this.dateFrom = dateFrom;
        this.dateTo = dateTo;
        this.totalAmount = totalAmount;
        this.driverNeed = driverNeed;
        this.flag = flag;
        this.user = user;
        this.vehicle = vehicle;
        PickupLocation = pickupLocation;
        DropoffLocation = dropoffLocation;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDateFrom() {
        return dateFrom;
    }

    public void setDateFrom(String dateFrom) {
        this.dateFrom = dateFrom;
    }

    public String getDateTo() {
        return dateTo;
    }

    public void setDateTo(String dateTo) {
        this.dateTo = dateTo;
    }

    public String getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(String totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getDriverNeed() {
        return driverNeed;
    }

    public void setDriverNeed(String driverNeed) {
        this.driverNeed = driverNeed;
    }

    public int getFlag() {
        return flag;
    }

    public void setFlag(int flag) {
        this.flag = flag;
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

    public Location getPickupLocation() {
        return PickupLocation;
    }

    public void setPickupLocation(Location pickupLocation) {
        PickupLocation = pickupLocation;
    }

    public Location getDropoffLocation() {
        return DropoffLocation;
    }

    public void setDropoffLocation(Location dropoffLocation) {
        DropoffLocation = dropoffLocation;
    }
}
