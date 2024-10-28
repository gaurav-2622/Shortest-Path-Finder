import java.util.List;

public class Main {
    public static void main(String[] args) {
        Graph graph = new Graph();
        // Adding edges
        graph.addEdge("A", "B", 1);
        graph.addEdge("A", "C", 4);
        graph.addEdge("B", "C", 2);
        graph.addEdge("B", "D", 5);
        graph.addEdge("C", "D", 1);
        graph.addEdge("D", "E", 3);
        graph.addEdge("C", "E", 6);

        String start = "A";
        String end = "E";

        try {
            List<String> path = graph.getShortestPath(start, end);
            // Call dijkstra and get the Pair object to access distances
            Graph.Pair result = graph.dijkstra(start);
            int distance = result.distances.get(end); // Retrieve the distance to the end node
            System.out.println("The shortest path from " + start + " to " + end + " is: " + String.join(" -> ", path) + " with a distance of " + distance);
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }
}
