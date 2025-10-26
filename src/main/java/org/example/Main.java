package org.example;
import org.example.graph.Graph;
import org.example.io.GraphLoader;
import org.example.io.ResultsWriter;
import org.example.algorithms.Kruskal;
import org.example.algorithms.Prim;
import org.example.Model.MSTResult;
import java.util.List;
public class Main {
    public static void main(String[] args) {
        System.out.println("===== MST Analysis Start =====");
        String[] graphFiles = {
                "src/main/resources/jsons/graphs_small.json",
                "src/main/resources/jsons/graphs_medium.json",
                "src/main/resources/jsons/graphs_large.json",
                "src/main/resources/jsons/graphs_extra_large.json"
        };
        for (String file : graphFiles) {
            System.out.println("\nLoading graphs from: " + file);
            List<Graph> graphs = GraphLoader.loadGraphs(file);
            if (graphs.isEmpty()) {
                System.out.println("Failed to load graphs from file: " + file);
                continue;
            }
            System.out.println("Loaded " + graphs.size() + " graphs successfully.");
            for (Graph graph : graphs) {
                MSTResult resultKruskal = Kruskal.run(graph);
                System.out.println("Kruskal result -> Cost: " + resultKruskal.getTotalCost() +
                        " | Time: " + resultKruskal.getExecutionTimeMs() + "ms" +
                        " | Ops: " + resultKruskal.getOperationsCount());
                ResultsWriter.writeToJson("results/Kruskal_" + graph.id() + ".json", resultKruskal);
                Prim prim = new Prim();
                MSTResult resultPrim = prim.computeMST(graph);
                System.out.println("Prim result -> Cost: " + resultPrim.getTotalCost() +
                        " | Time: " + resultPrim.getExecutionTimeMs() + "ms" +
                        " | Ops: " + resultPrim.getOperationsCount());
                ResultsWriter.writeToJson("results/Prim_" + graph.id() + ".json", resultPrim);
            }
        }
        System.out.println("\n===== MST Analysis Complete =====");
    }
}