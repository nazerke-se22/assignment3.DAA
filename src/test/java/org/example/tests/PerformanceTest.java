package org.example.tests;
import org.example.algorithms.Kruskal;
import org.example.algorithms.Prim;
import org.example.graph.Graph;
import org.example.io.GraphLoader;
import org.example.io.ResultsWriter;
import org.example.Model.MSTResult;
import org.junit.jupiter.api.Test;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;
public class PerformanceTest {
    @Test
    public void runBenchmarkAndProduceCsv() throws Exception {
        String[] files = {
                "src/main/resources/jsons/graphs_small.json",
                "src/main/resources/jsons/graphs_medium.json",
                "src/main/resources/jsons/graphs_large.json",
                "src/main/resources/jsons/graphs_extra_large.json"
        };
        List<String[]> csvRows = new ArrayList<>();
        csvRows.add(new String[]{"GraphID","Algorithm","Vertices","Edges","ExecutionTimeMs","OperationsCount","TotalCost"});
        for (String file : files) {
            List<Graph> graphs = GraphLoader.loadGraphs(file);
            for (Graph graph : graphs) {
                MSTResult kruskal = Kruskal.run(graph);
                MSTResult prim = new Prim().computeMST(graph);
                ResultsWriter.writeToJson("results/Kruskal_" + graph.id() + ".json", kruskal);
                ResultsWriter.writeToJson("results/Prim_" + graph.id() + ".json", prim);

                csvRows.add(new String[]{
                        graph.id(),
                        kruskal.getAlgorithm(),
                        String.valueOf(graph.V()),
                        String.valueOf(graph.E()),
                        String.valueOf(kruskal.getExecutionTimeMs()),
                        String.valueOf(kruskal.getOperationsCount()),
                        String.valueOf(kruskal.getTotalCost())
                });

                csvRows.add(new String[]{
                        graph.id(),
                        prim.getAlgorithm(),
                        String.valueOf(graph.V()),
                        String.valueOf(graph.E()),
                        String.valueOf(prim.getExecutionTimeMs()),
                        String.valueOf(prim.getOperationsCount()),
                        String.valueOf(prim.getTotalCost())
                });
            }
        }
        try (FileWriter fw = new FileWriter("results/mst_benchmark.csv")) {
            for (String[] row : csvRows) {
                fw.write(String.join(",", row) + "\n");
            }
        }
    }
}