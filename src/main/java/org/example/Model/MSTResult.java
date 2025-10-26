package org.example.Model;
import org.example.graph.Edge;
import java.util.List;
public class MSTResult {
    private final String algorithm;
    private final String graphId;
    private final List<Edge> edges;
    private final double totalCost;
    private final double executionTimeMs;
    private final long operationsCount;

    public MSTResult(String algorithm, String graphId, List<Edge> edges,
                     double totalCost, double executionTimeMs, long operationsCount) {
        this.algorithm = algorithm;
        this.graphId = graphId;
        this.edges = List.copyOf(edges);
        this.totalCost = totalCost;
        this.executionTimeMs = executionTimeMs;
        this.operationsCount = operationsCount;
    }

    public String getAlgorithm() { return algorithm; }
    public String getGraphId() { return graphId; }
    public List<Edge> getEdges() { return edges; }
    public double getTotalCost() { return totalCost; }
    public double getExecutionTimeMs() { return executionTimeMs; }
    public long getOperationsCount() { return operationsCount; }

    @Override
    public String toString() {
        return String.format("%s MST [Graph=%s, Cost=%.2f, Time=%.3f ms, Ops=%d]",
                algorithm, graphId, totalCost, executionTimeMs, operationsCount);
    }
}
