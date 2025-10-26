package org.example.Model;
import org.example.graph.Edge;
import java.util.List;

public class MSTOutput {
    public String graphId;
    public InputStats inputStats;
    public MSTData prim;
    public MSTData kruskal;
    public static class InputStats {
        public int vertices;
        public int edges;

        public InputStats(int vertices, int edges) {
            this.vertices = vertices;
            this.edges = edges;
        }
    }
    public static class MSTData {
        public List<Edge> mstEdges;
        public int totalCost;
        public long operationsCount;
        public double executionTimeMs;
        public MSTData(List<Edge> mstEdges, int totalCost, long operationsCount, double executionTimeMs) {
            this.mstEdges = mstEdges;
            this.totalCost = totalCost;
            this.operationsCount = operationsCount;
            this.executionTimeMs = executionTimeMs;
        }
    }
    public MSTOutput(String graphId, InputStats inputStats, MSTData prim, MSTData kruskal) {
        this.graphId = graphId;
        this.inputStats = inputStats;
        this.prim = prim;
        this.kruskal = kruskal;
    }
}