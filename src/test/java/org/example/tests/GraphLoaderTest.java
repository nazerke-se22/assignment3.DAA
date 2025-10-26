package org.example.tests;
import org.example.graph.Graph;
import org.example.io.GraphLoader;
import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;
public class GraphLoaderTest {
    @Test
    public void testLoadGraphsFromFiles() {
        String[] files = {
                "src/main/resources/jsons/graphs_small.json",
                "src/main/resources/jsons/graphs_medium.json",
                "src/main/resources/jsons/graphs_large.json",
                "src/main/resources/jsons/graphs_extra_large.json"
        };
        for (String file : files) {
            List<Graph> graphs = GraphLoader.loadGraphs(file);
            assertNotNull(graphs, "Returned list should not be null for file: " + file);
            assertFalse(graphs.isEmpty(), "There should be at least one graph in file: " + file);
            for (Graph g : graphs) {
                assertTrue(g.V() > 0, "Graph should have vertices");
                assertTrue(g.E() >= 0, "Graph should have edges (>=0)");
            }
        }
    }
}