package org.example.algorithms;
import org.example.graph.*;
import org.example.Model.MSTResult;
import java.util.*;

public class Prim {
    public MSTResult computeMST(Graph graph) {
        long operations = 0;
        long start = System.nanoTime();

        Set<String> visited = new HashSet<>();
        PriorityQueue<Edge> pq = new PriorityQueue<>();
        List<Edge> mstEdges = new ArrayList<>();
        int totalCost = 0;
        String startVertex = graph.getVertices().get(0);
        visited.add(startVertex);
        pq.addAll(graph.adj(startVertex));
        operations++;
        while (!pq.isEmpty() && mstEdges.size() < graph.V() - 1) {
            Edge e = pq.poll();
            operations++;
            if (visited.contains(e.getTo())) continue;
            mstEdges.add(e);
            totalCost += e.getWeight();
            visited.add(e.getTo());
            for (Edge next : graph.adj(e.getTo())) {
                pq.add(next);
                operations++;
            }
        }
        long end = System.nanoTime();
        long durationMs = (end - start) / 1_000_000;
        return new MSTResult("Prim", graph.id(), mstEdges,
                totalCost, durationMs, operations);
    }
}