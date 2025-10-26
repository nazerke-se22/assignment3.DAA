package org.example.algorithms;
import org.example.graph.Edge;
import org.example.graph.Graph;
import org.example.Model.MSTResult;
import java.util.*;
public class Prim {

    public MSTResult computeMST(Graph graph) {
        long startTime = System.nanoTime();
        long operations = 0;

        List<Edge> mstEdges = new ArrayList<>();
        int n = graph.V();
        if (n == 0) {
            return new MSTResult("Prim", String.valueOf(graph.id()), mstEdges, 0.0, 0.0, 0L);
        }

        boolean[] inMST = new boolean[n];
        PriorityQueue<Edge> pq = new PriorityQueue<>(Comparator.comparingInt(Edge::getWeight));

        // start vertex = first vertex in list
        String startVertex = graph.getVertices().get(0);
        int startIdx = graph.indexOf(startVertex);
        if (startIdx >= 0) inMST[startIdx] = true;

        Set<String> addedEdges = new HashSet<>();
        for (Edge e : graph.adj(startVertex)) {
            String key = e.getFrom() + "-" + e.getTo();
            if (addedEdges.add(key)) pq.add(e);
            operations++;
        }

        while (!pq.isEmpty() && mstEdges.size() < n - 1) {
            Edge e = pq.poll();
            operations++;

            int toIdx = graph.indexOf(e.getTo());
            int fromIdx = graph.indexOf(e.getFrom());
            if (toIdx >= 0 && fromIdx >= 0 && inMST[toIdx] && inMST[fromIdx]) {
                continue;
            }

            mstEdges.add(e);
            operations++;

            int newIdx = (toIdx >= 0 && !inMST[toIdx]) ? toIdx : fromIdx;
            if (newIdx >= 0) {
                inMST[newIdx] = true;
                String newVertex = graph.nameOf(newIdx);

                for (Edge next : graph.adj(newVertex)) {
                    int idx = graph.indexOf(next.getTo());
                    if (idx >= 0 && !inMST[idx]) {
                        String key = next.getFrom() + "-" + next.getTo();
                        if (addedEdges.add(key)) pq.add(next);
                        operations++;
                    }
                }
            }
        }

        long endTime = System.nanoTime();
        double durationMs = (endTime - startTime) / 1_000_000.0;
        double totalCost = mstEdges.stream().mapToDouble(Edge::getWeight).sum();

        return new MSTResult(
                "Prim",
                String.valueOf(graph.id()),
                mstEdges,
                totalCost,
                durationMs,
                operations
        );
    }
}
