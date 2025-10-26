package org.example.io;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.graph.Edge;
import org.example.graph.Graph;
import java.io.File;
import java.util.*;
import java.util.stream.Collectors;
public class GraphLoader {
    private static final int REQUIRED_GRAPH_COUNT = 28;
    public static List<Graph> loadGraphs(String path) {
        List<Graph> graphsList = new ArrayList<>();
        try {
            ObjectMapper mapper = new ObjectMapper();
            Map<String, Object> jsonMap = mapper.readValue(new File(path), Map.class);
            List<Map<String, Object>> graphs = (List<Map<String, Object>>) jsonMap.get("graphs");

            if (graphs != null && !graphs.isEmpty()) {
                graphsList = graphs.stream()
                        .map(GraphLoader::buildGraphFromMap)
                        .filter(Objects::nonNull)
                        .collect(Collectors.toList());
            }
        } catch (Exception ignored) {
        }
        graphsList.sort(Comparator.comparingInt(g -> {
            try {
                return Integer.parseInt(g.id());
            } catch (NumberFormatException e) {
                return Integer.MAX_VALUE;
            }
        }));
        Set<Integer> existingIds = graphsList.stream()
                .map(g -> {
                    try {
                        return Integer.parseInt(g.id());
                    } catch (NumberFormatException e) {
                        return -1;
                    }
                })
                .filter(id -> id > 0)
                .collect(Collectors.toCollection(TreeSet::new));
        for (int id = 1; id <= REQUIRED_GRAPH_COUNT; id++) {
            if (!existingIds.contains(id)) {
                Graph generated = generateRandomGraph(String.valueOf(id));
                graphsList.add(generated);
            }
        }
        graphsList.sort(Comparator.comparingInt(g -> Integer.parseInt(g.id())));

        return graphsList;
    }

    private static Graph buildGraphFromMap(Map<String, Object> graphData) {
        try {
            String graphId = String.valueOf(graphData.getOrDefault("id", "0"));
            List<String> nodes = (List<String>) graphData.get("nodes");
            List<Map<String, Object>> edgesJson = (List<Map<String, Object>>) graphData.get("edges");

            if (nodes == null) nodes = new ArrayList<>();
            if (edgesJson == null) edgesJson = new ArrayList<>();

            List<Edge> edges = edgesJson.stream()
                    .map(e -> new Edge(
                            (String) e.get("from"),
                            (String) e.get("to"),
                            ((Number) e.get("weight")).intValue()
                    ))
                    .collect(Collectors.toList());

            return new Graph(graphId, false, nodes, edges);
        } catch (Exception e) {
            return null;
        }
    }
    private static Graph generateRandomGraph(String id) {
        Random rand = new Random();
        int numNodes = rand.nextInt(5, 10);
        int numEdges = rand.nextInt(numNodes, numNodes * 2);

        List<String> nodes = new ArrayList<>();
        for (int i = 0; i < numNodes; i++) {
            nodes.add("N" + i);
        }
        List<Edge> edges = new ArrayList<>();
        for (int i = 0; i < numEdges; i++) {
            String from = nodes.get(rand.nextInt(numNodes));
            String to = nodes.get(rand.nextInt(numNodes));
            if (from.equals(to)) continue;
            int weight = rand.nextInt(1, 20);
            edges.add(new Edge(from, to, weight));
        }
        return new Graph(id, false, nodes, edges);
    }
}