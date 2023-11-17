package com.example.notuber.Model;

/**
 * Clase ApiResponse representa la respuesta de una API para información de un conductor.
 */
public class ApiResponse {

    private String location;        // Ubicación del conductor
    private long totalDistance;     // Distancia total recorrida por el conductor

    private String rating;          // Calificación del conductor (utilizado en getDriverRating)
    private int rides;               // Número de viajes realizados por el conductor (utilizado en getDriverRides)

    /**
     * Obtiene la ubicación del conductor.
     *
     * @return La ubicación del conductor.
     */
    public String getLocation() {
        return location;
    }

    /**
     * Obtiene la distancia total recorrida por el conductor.
     *
     * @return La distancia total recorrida por el conductor.
     */
    public long getTotalDistance() {
        return totalDistance;
    }

    /**
     * Obtiene la calificación del conductor.
     *
     * @return La calificación del conductor.
     */
    public String getRating() {
        return rating;
    }

    /**
     * Obtiene el número de viajes realizados por el conductor.
     *
     * @return El número de viajes realizados por el conductor.
     */
    public int getRides() {
        return rides;
    }
}

