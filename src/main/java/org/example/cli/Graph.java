package org.example.cli;

public class Graph {
    private final String id;
    private final boolean directed;
    private final List<String> vertices;
    private final List<Edge> edges;
    private final Map<String, List<Edge>> adj; // Список смежности
    private final Map<String, Integer> indexMap;

    public Graph(String id, boolean directed, List<String> vertices, List<Edge> edges) {
        this.id = id;
        this.directed = directed;
        this.vertices = new ArrayList<>(vertices);
        this.edges = new ArrayList<>(edges);
        this.adj = new HashMap<>();
        this.indexMap = new HashMap<>();

        // Инициализация смежности и индексов
        for (int i = 0; i < vertices.size(); i++) {
            String vertex = vertices.get(i);
            indexMap.put(vertex, i);
            adj.put(vertex, new ArrayList<>());
        }

        // Добавляем рёбра
        for (Edge e : edges) {
            adj.get(e.getFrom()).add(e); // Добавляем ребро в смежность
            if (!directed) {
                adj.get(e.getTo()).add(e); // Для неориентированного графа добавляем обратное ребро
            }
        }
    }

    public String id() {
        return id;
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