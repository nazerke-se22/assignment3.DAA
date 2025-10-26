package org.example.tests;
import org.example.algorithms.Kruskal;
import org.example.algorithms.Prim;
import org.example.graph.*;
import org.example.io.GraphLoader;
import org.example.Model.MSTResult;
import org.junit.jupiter.api.Test;
import java.util.*;
import static org.junit.jupiter.api.Assertions.*;

public class CorrectnessTest {
    @Test
    public void testMSTCorrectnessForAllGraphs() {
        String[] files = {
                "src/main/resources/jsons/graphs_small.json",
                "src/main/resources/jsons/graphs_medium.json",
                "src/main/resources/jsons/graphs_large.json",
                "src/main/resources/jsons/graphs_extra_large.json"
        };
        for (String file : files) {
            List<Graph> graphs = GraphLoader.loadGraphs(file);
            for (Graph graph : graphs) {
                assertNotNull(graph, "Graph must not be null: " + file);
                if (!isConnected(graph)) {
                    System.out.println("Skipping disconnected graph: " + graph.id());
                    continue;
                }
                MSTResult kruskal = Kruskal.run(graph);
                MSTResult prim = new Prim().computeMST(graph);

                validateMST(graph, kruskal);
                validateMST(graph, prim);

                assertEquals(kruskal.getTotalCost(), prim.getTotalCost(),
                        "MST total cost must be identical for graph " + graph.id());
            }
        }
    }
    private void validateMST(Graph graph, MSTResult mst) {
        List<Edge> edges = mst.getEdges();
        List<String> vertices = graph.getVertices();

        assertEquals(vertices.size() - 1, edges.size(), "MST must have V-1 edges");

        DisjointSet uf = new DisjointSet();
        for (String v : vertices) uf.add(v);
        for (Edge e : edges) {
            String r1 = uf.find(e.getFrom());
            String r2 = uf.find(e.getTo());
            assertNotEquals(r1, r2, "MST must be acyclic");
            uf.disjoint(e.getFrom(), e.getTo());
        }
        String root = uf.find(vertices.get(0));
        for (String v : vertices) {
            assertEquals(root, uf.find(v), "MST must connect all vertices");
        }

        assertTrue(mst.getExecutionTimeMs() >= 0, "ExecutionTimeMs must be non-negative");
        assertTrue(mst.getOperationsCount() >= 0, "OperationsCount must be non-negative");
    }
    private boolean isConnected(Graph graph) {
        if (graph.V() == 0) return true;
        Set<String> visited = new HashSet<>();
        Queue<String> queue = new LinkedList<>();
        String start = graph.getVertices().get(0);
        visited.add(start);
        queue.add(start);

        while (!queue.isEmpty()) {
            String v = queue.poll();
            for (Edge e : graph.adj(v)) {
                String u = e.getTo();
                if (!visited.contains(u)) {
                    visited.add(u);
                    queue.add(u);
                }
            }
        }
        return visited.size() == graph.V();
    }
}