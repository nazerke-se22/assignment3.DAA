package org.example.Model;
import org.example.graph.Edge;
import java.util.List;
public class MSTOutput {

    private Object graph_id;
    private InputStats input_stats;
    private MSTData prim;
    private MSTData kruskal;

    public MSTOutput(Object graph_id, InputStats input_stats, MSTData prim, MSTData kruskal) {
        this.graph_id = graph_id;
        this.input_stats = input_stats;
        this.prim = prim;
        this.kruskal = kruskal;
    }

    public Object getGraph_id() { return graph_id; }
    public InputStats getInput_stats() { return input_stats; }
    public MSTData getPrim() { return prim; }
    public MSTData getKruskal() { return kruskal; }

    public static class InputStats {
        private int vertices;
        private int edges;

        public InputStats(int vertices, int edges) {
            this.vertices = vertices;
            this.edges = edges;
        }

        public int getVertices() { return vertices; }
        public int getEdges() { return edges; }
    }

    public static class MSTData {
        private List<Edge> mst_edges;
        private double total_cost;
        private long operations_count;
        private double execution_time_ms;

        public MSTData(List<Edge> mst_edges, double total_cost, long operations_count, double execution_time_ms) {
            this.mst_edges = List.copyOf(mst_edges);
            this.total_cost = total_cost;
            this.operations_count = operations_count;
            this.execution_time_ms = execution_time_ms;
        }

        public List<Edge> getMst_edges() { return mst_edges; }
        public double getTotal_cost() { return total_cost; }
        public long getOperations_count() { return operations_count; }
        public double getExecution_time_ms() { return execution_time_ms; }
    }
}
