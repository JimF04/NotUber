package com.example.notuber.Model;

import java.util.List;

public class NodesResponse {
    private List<NodeMarker> nodes;

    public NodesResponse(List<NodeMarker> nodes) {
        this.nodes = nodes;
    }

    public List<NodeMarker> getNodes() {
        return nodes;
    }

    public void setNodes(List<NodeMarker> nodes) {
        this.nodes = nodes;
    }
}

