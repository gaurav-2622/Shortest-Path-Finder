import java.util.*;

public class Graph {
    private final Map<String, List<Edge>> adjacencyList = new HashMap<>();

    private static class Edge {
        String target;
        int weight;

        Edge(String target, int weight) {
            this.target = target;
            this.weight = weight;
        }
    }

    // Method to add edges to the graph
    public void addEdge(String u, String v, int weight) {
        adjacencyList.putIfAbsent(u, new ArrayList<>());
        adjacencyList.get(u).add(new Edge(v, weight));
        adjacencyList.putIfAbsent(v, new ArrayList<>()); // Ensure v is also in the graph
    }

    // Create a simple Pair class to hold both distance and previous nodes
    static class Pair {
        Map<String, Integer> distances;
        Map<String, String> previousNodes;

        Pair(Map<String, Integer> distances, Map<String, String> previousNodes) {
            this.distances = distances;
            this.previousNodes = previousNodes;
        }
    }

    // Dijkstra's algorithm to find the shortest path
    public Pair dijkstra(String start) {
        if (!adjacencyList.containsKey(start)) {
            throw new IllegalArgumentException("Start node does not exist in the graph");
        }

        // Create a priority queue for nodes to visit
        PriorityQueue<Node> pq = new PriorityQueue<>(Comparator.comparingInt(node -> node.distance));
        Map<String, Integer> distances = new HashMap<>();
        Map<String, String> previousNodes = new HashMap<>();

        // Initialize distances and add the start node
        for (String node : adjacencyList.keySet()) {
            distances.put(node, Integer.MAX_VALUE);
        }
        distances.put(start, 0);
        pq.offer(new Node(start, 0));

        while (!pq.isEmpty()) {
            Node current = pq.poll();

            // Iterate over neighbors
            for (Edge edge : adjacencyList.get(current.name)) {
                int newDist = distances.get(current.name) + edge.weight;
                if (newDist < distances.get(edge.target)) {
                    distances.put(edge.target, newDist);
                    previousNodes.put(edge.target, current.name);
                    pq.offer(new Node(edge.target, newDist));
                }
            }
        }

        return new Pair(distances, previousNodes); // Return both distances and previous nodes
    }

    // Method to get the shortest path from start to end
    public List<String> getShortestPath(String start, String end) {
        List<String> path = new ArrayList<>();
        Pair result = dijkstra(start); // Get shortest paths

        Map<String, String> previousNodes = result.previousNodes;

        for (String at = end; at != null; at = previousNodes.get(at)) {
            path.add(at);
        }
        Collections.reverse(path);

        // If the start node is not in the path, return an empty list
        if (path.isEmpty() || !path.get(0).equals(start)) {
            throw new IllegalArgumentException("One or both nodes do not exist in the graph");
        }

        return path;
    }

    // Helper class for the priority queue
    private static class Node {
        String name;
        int distance;

        Node(String name, int distance) {
            this.name = name;
            this.distance = distance;
        }
    }
}
