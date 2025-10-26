package org.example.graph;
import java.util.*;
public class Graph {
    private String id;
    private final boolean directed;
    private final List<String> vertices;
    private final List<Edge> edges;
    private final Map<String, List<Edge>> adj;
    private final Map<String, Integer> indexMap;

    public Graph(String id, boolean directed, List<String> vertices, List<Edge> edges) {
        this.id = id;
        this.directed = directed;
        this.vertices = new ArrayList<>(vertices);
        this.edges = new ArrayList<>(edges);
        this.adj = new HashMap<>();
        this.indexMap = new HashMap<>();
        for (int i = 0; i < vertices.size(); i++) {
            String vertex = vertices.get(i);
            indexMap.put(vertex, i);
            adj.put(vertex, new ArrayList<>());
        }
        for (Edge e : edges) {
            adj.get(e.getFrom()).add(e);
            if (!directed) {
                adj.get(e.getTo()).add(new Edge(e.getTo(), e.getFrom(), e.getWeight()));
            }
        }
    }
    public String id() {
        return id;
    }
    public void setId(String newId) {
        this.id = newId;
    }
    public int V() {
        return vertices.size();
    }
    public int E() {
        return edges.size();
    }
    public List<String> getVertices() {
        return Collections.unmodifiableList(vertices);
    }
    public List<Edge> edges() {
        return Collections.unmodifiableList(edges);
    }
    public List<Edge> adj(String vertex) {
        return Collections.unmodifiableList(adj.getOrDefault(vertex, List.of()));
    }
    public List<Edge> adj(int index) {
        String vertex = vertices.get(index);
        return adj(vertex);
    }
    public int indexOf(String vertex) {
        return indexMap.get(vertex);
    }
    public String nameOf(int index) {
        return vertices.get(index);
    }
    public boolean isDirected() {
        return directed;
    }
    @Override
    public String toString() {
        return "Graph{id='" + id + "', V=" + V() + ", E=" + E() + "}";
    }
}