package org.example.benchmark;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.algorithms.Kruskal;
import org.example.algorithms.Prim;
import org.example.graph.Graph;
import org.example.Model.MSTOutput;
import org.example.Model.MSTResult;
import org.example.io.GraphLoader;
import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.*;

public class BenchmarkRunner {
    private static final int REPEAT_COUNT = 100;
    public static void main(String[] args) throws Exception {
        String[] files = {
                "src/main/resources/jsons/graphs_small.json",
                "src/main/resources/jsons/graphs_medium.json",
                "src/main/resources/jsons/graphs_large.json",
                "src/main/resources/jsons/graphs_extra_large.json"
        };

        List<MSTOutput> outputs = new ArrayList<>();
        Prim primAlg = new Prim();
        List<CSVRecord> csvRecords = new ArrayList<>();

        for (String file : files) {
            System.out.println("Loading graphs from: " + file);
            List<Graph> graphs = GraphLoader.loadGraphs(file);
            if (graphs == null || graphs.isEmpty()) continue;

            for (Graph g : graphs) {
                if (!isConnected(g)) continue;

                System.out.println("Running algorithms for graph: " + g.id());

                MSTResult bestPrim = null, bestKruskal = null;
                double totalPrim = 0, totalKruskal = 0;

                for (int i = 0; i < REPEAT_COUNT; i++) {
                    MSTResult prim = primAlg.computeMST(g);
                    MSTResult kruskal = Kruskal.run(g);

                    totalPrim += prim.getExecutionTimeMs();
                    totalKruskal += kruskal.getExecutionTimeMs();

                    if (bestPrim == null) bestPrim = prim;
                    if (bestKruskal == null) bestKruskal = kruskal;
                }

                double avgPrimTime = totalPrim / REPEAT_COUNT;
                double avgKruskalTime = totalKruskal / REPEAT_COUNT;

                MSTOutput.InputStats stats = new MSTOutput.InputStats(g.V(), g.E());
                MSTOutput.MSTData primData = new MSTOutput.MSTData(
                        bestPrim.getEdges(),
                        bestPrim.getTotalCost(),
                        bestPrim.getOperationsCount(),
                        avgPrimTime
                );
                MSTOutput.MSTData kruskalData = new MSTOutput.MSTData(
                        bestKruskal.getEdges(),
                        bestKruskal.getTotalCost(),
                        bestKruskal.getOperationsCount(),
                        avgKruskalTime
                );

                outputs.add(new MSTOutput(g.id(), stats, primData, kruskalData));

                csvRecords.add(new CSVRecord(g.id(), "Kruskal", g.V(), g.E(),
                        avgKruskalTime, bestKruskal.getOperationsCount(), bestKruskal.getTotalCost()));
                csvRecords.add(new CSVRecord(g.id(), "Prim", g.V(), g.E(),
                        avgPrimTime, bestPrim.getOperationsCount(), bestPrim.getTotalCost()));

                System.out.printf(Locale.US,
                        "Graph %s → Kruskal=%.6f ms | Prim=%.6f ms%n",
                        g.id(), avgKruskalTime, avgPrimTime);
            }
        }

        ObjectMapper mapper = new ObjectMapper();
        File jsonFile = new File("src/main/resources/jsons/output.json");
        jsonFile.getParentFile().mkdirs();
        mapper.writerWithDefaultPrettyPrinter()
                .writeValue(jsonFile, Map.of("results", outputs));

        File csvFile = new File("src/main/resources/jsons/benchmark_results.csv");
        try (PrintWriter writer = new PrintWriter(new FileWriter(csvFile))) {
            writer.println("GraphID,Algorithm,Vertices,Edges,ExecutionTimeMs,OperationsCount,TotalCost");
            for (CSVRecord r : csvRecords) {
                writer.printf(Locale.US, "%s,%s,%d,%d,%.6f,%d,%.2f%n",
                        r.graphId, r.algorithm, r.vertices, r.edges,
                        r.executionTimeMs, r.operationsCount, r.totalCost);
            }
        }

        System.out.println("Benchmark finished. Results saved to CSV & JSON.");
    }

    private static boolean isConnected(Graph graph) {
        if (graph.V() == 0) return true;
        Set<String> visited = new HashSet<>();
        Queue<String> queue = new LinkedList<>();
        String start = graph.getVertices().get(0);
        visited.add(start);
        queue.add(start);

        while (!queue.isEmpty()) {
            String v = queue.poll();
            for (var e : graph.adj(v)) {
                String u = e.getTo();
                if (!visited.contains(u)) {
                    visited.add(u);
                    queue.add(u);
                }
            }
        }
        return visited.size() == graph.V();
    }

    private static class CSVRecord {
        String graphId;
        String algorithm;
        int vertices;
        int edges;
        double executionTimeMs;
        long operationsCount;
        double totalCost;

        public CSVRecord(Object graphId, String algorithm, int vertices, int edges,
                         double executionTimeMs, long operationsCount, double totalCost) {
            this.graphId = String.valueOf(graphId);
            this.algorithm = algorithm;
            this.vertices = vertices;
            this.edges = edges;
            this.executionTimeMs = executionTimeMs;
            this.operationsCount = operationsCount;
            this.totalCost = totalCost;
        }
    }
}
