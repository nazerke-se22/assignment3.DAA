package org.example.graph;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
public class Edge implements Comparable<Edge> {
    private final String from;
    private final String to;
    private final int weight;

    @JsonCreator
    public Edge(@JsonProperty("from") String from,
                @JsonProperty("to") String to,
                @JsonProperty("weight") int weight) {
        this.from = from;
        this.to = to;
        this.weight = weight;
    }
    public String getFrom() { return from; }
    public String getTo() { return to; }
    public int getWeight() { return weight; }

    public Edge reversed() {
        return new Edge(to, from, weight);
    }
    @Override
    public int compareTo(Edge o) {
        return Integer.compare(this.weight, o.weight);
    }
    @Override
    public String toString() {
        return String.format("%s-%s(%d)", from, to, weight);
    }
}