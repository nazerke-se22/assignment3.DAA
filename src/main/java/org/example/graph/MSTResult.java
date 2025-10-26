package org.example.graph;
import java.util.List;
public class MSTResult {
    public final List<Edge> edges;
    public final int totalCost;
    public final int V;
    public final int E;
    public final long timeMs;
    public final String algorithm;
    public final String graphId;

    public MSTResult(List<Edge> edges, int totalCost, int V, int E,
                     long timeMs, String algorithm, String graphId) {
        this.edges = edges;
        this.totalCost = totalCost;
        this.V = V;
        this.E = E;
        this.timeMs = timeMs;
        this.algorithm = algorithm;
        this.graphId = graphId;
    }
}