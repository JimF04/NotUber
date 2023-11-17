package com.example.notuber.Model;

import com.google.android.gms.maps.model.LatLng;

/**
 * La clase NodeMarker representa un marcador en el mapa con un nombre y coordenadas de latitud y longitud.
 */
public class NodeMarker {
    private String name;
    private double latitude;
    private double longitude;

    /**
     * Constructor que inicializa la clase NodeMarker con un nombre, latitud y longitud.
     *
     * @param name      El nombre asociado al marcador.
     * @param latitude  La latitud asociada al marcador.
     * @param longitude La longitud asociada al marcador.
     */
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
