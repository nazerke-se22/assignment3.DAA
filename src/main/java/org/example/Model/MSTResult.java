package org.example.Model;
import org.example.graph.Edge;
import java.util.List;
public class MSTResult {
    private final String algorithm;
    private final List<Edge> edges;
    private final int totalCost;
    private final long executionTimeMs;
    private final long operationsCount;
    private final String graphId;
    public MSTResult(String algorithm, String graphId, List<Edge> edges,
                     int totalCost, long executionTimeMs, long operationsCount) {
        this.algorithm = algorithm;
        this.graphId = graphId;
        this.edges = List.copyOf(edges);
        this.totalCost = totalCost;
        this.executionTimeMs = executionTimeMs;
        this.operationsCount = operationsCount;
    }
    public String getAlgorithm() {
        return algorithm;
    }
    public List<Edge> getEdges() {
        return edges;
    }
    public int getTotalCost() {
        return totalCost;
    }
    public long getExecutionTimeMs() {
        return executionTimeMs;
    }
    public long getOperationsCount() {
        return operationsCount;
    }
    public String getGraphId() {
        return graphId;
    }
    @Override
    public String toString() {
        return algorithm + " MST: cost=" + totalCost +
                ", edges=" + edges.size() +
                ", time=" + executionTimeMs +
                "ms, ops=" + operationsCount;
    }
}