package org.example.algorithms;
import org.example.graph.Graph;
import org.example.graph.Edge;
import java.util.*;
public class Prim {
    public static List<Edge> findMST(Graph graph) {
        List<Edge> mstEdges = new ArrayList<>();
        int numVertices = graph.V();
        boolean[] inMST = new boolean[numVertices];
        PriorityQueue<Edge> pq = new PriorityQueue<>(Comparator.comparingInt(Edge::getWeight));
        inMST[0] = true;

        for (Edge edge : graph.edges()) {
            if (edge.getFrom().equals(graph.nameOf(0))) {
                pq.add(edge);
            }
        }
        while (!pq.isEmpty()) {
            Edge edge = pq.poll();
            if (!inMST[graph.indexOf(edge.getTo())]) {
                mstEdges.add(edge);
                inMST[graph.indexOf(edge.getTo())] = true;

                for (Edge nextEdge : graph.edges()) {
                    if (nextEdge.getFrom().equals(edge.getTo()) && !inMST[graph.indexOf(nextEdge.getTo())]) {
                        pq.add(nextEdge);
                    }
                }
            }
        }
        return mstEdges;
    }
    public static List<Edge> run(Graph graph) {
        return findMST(graph);
    }
}