package com.example.notuber.Model;

public class Driver {

    private String companyID;
    private String name;
    private String email;
    private String password;
    private double rating;
    private int rides;
    private String location;

    public String getCompanyID() {
        return companyID;
    }

    public void setCompanyID(String companyID) {
        this.companyID = companyID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public double getRating() {
        return rating;
    }
    public void setRating(double rating) {
        this.rating = rating;
    }
    public int getRides() {
        return rides;
    }
    public void setRides(int rides) {
        this.rides = rides;
    }

}
