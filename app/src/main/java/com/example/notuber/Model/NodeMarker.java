package com.example.notuber.Model;

import com.google.android.gms.maps.model.LatLng;

public class NodeMarker {
    private String name;
    private double latitude;
    private double longitude;

    public NodeMarker(String name, double latitude, double longitude) {
        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public String getName() {
        return name;
    }

    public LatLng getPosition() {
        return new LatLng(latitude, longitude);
    }
}
