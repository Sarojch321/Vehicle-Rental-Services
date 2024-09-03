package com.vehiclerentalservices.model;

public class Vehicle {
    private int id;
    private String name;
    private String type;
    private String amount;
    private int status;
    private String insurancephoto;
    private String bluebookphoto;
    private String driverstatus;

    User user;

    public Vehicle(int id, String name, String type, String amount, int status, String insurancephoto, String bluebookphoto, String driverstatus, User user) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.amount = amount;
        this.status = status;
        this.insurancephoto = insurancephoto;
        this.bluebookphoto = bluebookphoto;
        this.driverstatus = driverstatus;
        this.user = user;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getInsurancephoto() {
        return insurancephoto;
    }

    public void setInsurancephoto(String insurancephoto) {
        this.insurancephoto = insurancephoto;
    }

    public String getBluebookphoto() {
        return bluebookphoto;
    }

    public void setBluebookphoto(String bluebookphoto) {
        this.bluebookphoto = bluebookphoto;
    }

    public String getDriverstatus() {
        return driverstatus;
    }

    public void setDriverstatus(String driverstatus) {
        this.driverstatus = driverstatus;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
