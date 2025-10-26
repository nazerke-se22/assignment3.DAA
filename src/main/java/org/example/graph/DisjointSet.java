package org.example.graph;
import java.util.HashMap;
import java.util.Map;
public class DisjointSet {
    private final Map<String, String> parent = new HashMap<>();
    public void add(String x) {
        parent.putIfAbsent(x, x);
    }
    public String find(String x) {
        if (!parent.get(x).equals(x)) {
            parent.put(x, find(parent.get(x)));
        }
        return parent.get(x);
    }
    public void disjoint(String a, String b) {
        String rootA = find(a);
        String rootB = find(b);
        if (!rootA.equals(rootB)) {
            parent.put(rootA, rootB);
        }
    }
}