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

            if (graphs == null || graphs.isEmpty()) {
                System.out.println("No graphs found in " + file);
                continue;
            }

            for (Graph g : graphs) {
                if (!isConnected(g)) {
                    System.out.println("Skipping disconnected graph: " + g.id());
                    continue;
                }

                System.out.println("Running algorithms for graph: " + g.id());
                MSTResult prim = primAlg.computeMST(g);
                MSTResult kruskal = Kruskal.run(g);

                MSTOutput.InputStats stats = new MSTOutput.InputStats(g.V(), g.E());
                MSTOutput.MSTData primData = new MSTOutput.MSTData(
                        prim.getEdges(),
                        prim.getTotalCost(),
                        prim.getOperationsCount(),
                        prim.getExecutionTimeMs()
                );
                MSTOutput.MSTData kruskalData = new MSTOutput.MSTData(
                        kruskal.getEdges(),
                        kruskal.getTotalCost(),
                        kruskal.getOperationsCount(),
                        kruskal.getExecutionTimeMs()
                );

                outputs.add(new MSTOutput(g.id(), stats, primData, kruskalData));

                csvRecords.add(new CSVRecord(g.id(), "Kruskal", g.V(), g.E(),
                        kruskal.getExecutionTimeMs(), kruskal.getOperationsCount(), kruskal.getTotalCost()));
                csvRecords.add(new CSVRecord(g.id(), "Prim", g.V(), g.E(),
                        prim.getExecutionTimeMs(), prim.getOperationsCount(), prim.getTotalCost()));
            }
        }

        ObjectMapper mapper = new ObjectMapper();
        Map<String, Object> resultMap = new LinkedHashMap<>();
        resultMap.put("results", outputs);

        File jsonFile = new File("src/main/resources/jsons/output.json");
        jsonFile.getParentFile().mkdirs();
        mapper.writerWithDefaultPrettyPrinter().writeValue(jsonFile, resultMap);
        System.out.println("JSON saved to: " + jsonFile.getPath());

        File csvFile = new File("src/main/resources/jsons/benchmark_results.csv");
        try (PrintWriter writer = new PrintWriter(new FileWriter(csvFile))) {
            writer.println("GraphID,Algorithm,Vertices,Edges,ExecutionTimeMs,OperationsCount,TotalCost");
            for (CSVRecord r : csvRecords) {
                writer.printf(Locale.US, "%s,%s,%d,%d,%.0f,%d,%.0f%n",
                        r.graphId, r.algorithm, r.vertices, r.edges,
                        r.executionTimeMs, r.operationsCount, r.totalCost);
            }
        }
        System.out.println("CSV saved to: " + csvFile.getPath());
        System.out.println("All benchmark results successfully generated!");
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
