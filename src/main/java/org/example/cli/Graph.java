package org.example.cli;

import java.util.ArrayList;
import java.util.List;

public class Graph {
    private List<Edge> edges;
    private int numVertices;


    public Graph(int numVertices) {
        this.numVertices = numVertices;
        this.edges = new ArrayList<>();
    }


    public void addEdge(int vertex1, int vertex2, int weight) {
        edges.add(new Edge(vertex1, vertex2, weight));
    }

    public List<Edge> getEdges() {
        return edges;
    }

    public int getNumVertices() {
        return numVertices;
    }

    public List<Edge> getEdgesFromVertex(int vertex) {
        List<Edge> result = new ArrayList<>();
        for (Edge edge : edges) {
            if (edge.getVertex1() == vertex || edge.getVertex2() == vertex) {
                result.add(edge);
            }
        }
        return result;
    }
}