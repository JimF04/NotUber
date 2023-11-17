package com.example.notuber.Model;

public class ApiResponse {
    private String location; // Puedes agregar más campos según sea necesario
    private long totalDistance;

    private String rating; // Para getDriverRating
    private int rides; // Para getDriverRides

    public String getLocation() {
        return location;
    }

    public long getTotalDistance() {
        return totalDistance;
    }

    public String getRating() {
        return rating;
    }

    public int getRides() {
        return rides;
    }
}

