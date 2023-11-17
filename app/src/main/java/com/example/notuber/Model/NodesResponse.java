package com.example.notuber.Model;

import java.util.List;

/**
 * La clase NodesResponse representa la respuesta que contiene una lista de marcadores de nodos.
 */
public class NodesResponse {
    private List<NodeMarker> nodes;


    /**
     * Constructor que inicializa la clase NodesResponse con una lista de nodos.
     *
     * @param nodes La lista de nodos asociada a la respuesta.
     */
    public NodesResponse(List<NodeMarker> nodes) {
        this.nodes = nodes;
    }

    /**
     * Obtiene la lista de nodos asociada a la respuesta.
     *
     * @return La lista de nodos en la respuesta.
     */
    public List<NodeMarker> getNodes() {
        return nodes;
    }


    /**
     * Establece la lista de nodos asociada a la respuesta.
     *
     * @param nodes La nueva lista de nodos para la respuesta.
     */
    public void setNodes(List<NodeMarker> nodes) {
        this.nodes = nodes;
    }
}

