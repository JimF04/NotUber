package com.example.notuber.Model;

/**
 * La clase ApiResponse representa la respuesta de una API con información sobre una ubicación y distancia total.
 * Puedes agregar más campos según sea necesario para representar la información de la respuesta de la API.
 */
public class ApiResponse {
    /**
     * Ubicación asociada a la respuesta de la API.
     */
    private String location;

    /**
     * Distancia total asociada a la respuesta de la API.
     */
    private long totalDistance;

    /**
     * Obtiene la ubicación asociada a la respuesta de la API.
     *
     * @return La ubicación asociada a la respuesta de la API.
     */
    public String getLocation() {
        return location;
    }

    /**
     * Obtiene la distancia total asociada a la respuesta de la API.
     *
     * @return La distancia total asociada a la respuesta de la API.
     */
    public long getTotalDistance() {
        return totalDistance;
    }
}

