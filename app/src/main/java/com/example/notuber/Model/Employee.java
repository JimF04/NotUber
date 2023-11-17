package com.example.notuber.Model;

/**
 * La clase Employee representa a un empleado con información asociada como nombre, correo electrónico,
 * contraseña, ubicación, calificación y un identificador de la compañía.
 */
public class Employee {

    private String companyID;
    private String name;
    private String email;
    private String password;
    private String location;
    private double rating;

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

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public double getRating() {
        return rating;
    }
    public void setRating(double rating) {
        this.rating = rating;
    }
}
