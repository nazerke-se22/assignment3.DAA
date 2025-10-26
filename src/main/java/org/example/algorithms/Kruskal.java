package org.example.algorithms;
import org.example.graph.Edge;
import org.example.graph.Graph;
import org.example.graph.DisjointSet;
import org.example.Model.MSTResult;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
public class Kruskal {
    public static MSTResult run(Graph graph) {
        long startTime = System.nanoTime();
        long operations = 0;

        List<Edge> edges = new ArrayList<>(graph.edges()); // предполагаем, что graph.edges() возвращает Collection<Edge>
        Collections.sort(edges); // Edge должен реализовывать Comparable по весу
        operations += edges.size(); // пример: сортировка — считаем сравнения как edges.size()

        DisjointSet uf = new DisjointSet();
        for (String v : graph.getVertices()) {
            uf.add(v);
            operations++;
        }

        List<Edge> mstEdges = new ArrayList<>();
        double totalCost = 0.0;

        for (Edge e : edges) {
            operations++;
            String from = e.getFrom();
            String to = e.getTo();
            if (!uf.find(from).equals(uf.find(to))) {
                uf.disjoint(from, to);
                mstEdges.add(e);
                totalCost += e.getWeight();
            }
            if (mstEdges.size() == graph.V() - 1) break;
        }

        long endTime = System.nanoTime();
        double durationMs = (endTime - startTime) / 1_000_000.0; // double ms with fractions

        return new MSTResult(
                "Kruskal",
                String.valueOf(graph.id()),
                mstEdges,
                totalCost,
                durationMs,
                operations
        );
    }
}
