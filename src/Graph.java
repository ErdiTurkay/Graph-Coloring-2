import java.util.ArrayList;
import java.util.List;

class Graph
{
    // A list of lists to represent an adjacency list
    List<List<Integer>> adjList = null;

    Graph(List<Edge> edges, int n)
    {
        adjList = new ArrayList<>();

        // Creates an adjacency list for each vertex.
        for (int i = 0; i < n; i++) {
            adjList.add(new ArrayList<>());
        }

        // Add edges to the undirected graph.
        for (Edge edge: edges)
        {
            int src = edge.source;
            int dest = edge.dest;

            adjList.get(src).add(dest);
            // Since the graph is undirected, we also consider the reverse.
            adjList.get(dest).add(src);
        }
    }
}