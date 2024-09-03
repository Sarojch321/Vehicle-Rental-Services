package com.vehiclerentalservices.model;

public class Location {

    private int id;
    private String district;
    private String city;
    private String ward;
    private String tole;

    public Location(int id, String district, String city, String ward, String tole) {
        this.id = id;
        this.district = district;
        this.city = city;
        this.ward = ward;
        this.tole = tole;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getWard() {
        return ward;
    }

    public void setWard(String ward) {
        this.ward = ward;
    }

    public String getTole() {
        return tole;
    }

    public void setTole(String tole) {
        this.tole = tole;
    }
}
