import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

class Main{

    // Function to assign colors to vertices of a graph
    public static void colorGraph(Graph graph, int n)
    {
        // keep track of the color assigned to each vertex
        Map<Integer, Integer> result = new HashMap<>();

        // assign a color to vertex one by one
        for (int u = 0; u < n; u++)
        {
            // set to store the color of adjacent vertices of `u`
            Set<Integer> assigned = new TreeSet<>();

            // check colors of adjacent vertices of `u` and store them in a set
            for (int i: graph.adjList.get(u))
            {
                if (result.containsKey(i)) {
                    assigned.add(result.get(i));
                }
            }

            // check for the first free color
            int color = 0;
            for (Integer c: assigned)
            {
                if (color != c) {
                    break;
                }
                color++;
            }

            // assign vertex `u` the first available color
            result.put(u, color);
        }

        String colors = "";
        int optimal = 0;

        for (int v = 0; v < n; v++)
        {
            colors += result.get(v) + " ";

            if(result.get(v)>optimal)
                optimal = result.get(v);
        }

        System.out.println(optimal);
        System.out.println(colors);
    }

    // Greedy coloring of a graph
    public static void main(String[] args)
    {
        File file = new File("sample2.txt");
        int vertexNumber = 0;
        ArrayList edgeList = new ArrayList();

        try {
            try (BufferedReader br = new BufferedReader(new FileReader(file))) {
                String line;
                while ((line = br.readLine()) != null) {
                    String[] columns = line.split(" ");
                    if(columns[0].equals("p")) {
                        vertexNumber = Integer.parseInt(columns[1]);
                        break;
                    }
                }
            }

            try (BufferedReader br = new BufferedReader(new FileReader(file))) {
                String line;
                while ((line = br.readLine()) != null) {
                    String[] columns = line.split(" ");
                    if(columns[0].equals("e")) {
                        Edge newEdge = new Edge(Integer.parseInt(columns[1])-1, Integer.parseInt(columns[2])-1);
                        edgeList.add(newEdge);
                    }
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        // build a graph from the given edges
        Graph graph = new Graph(edgeList, vertexNumber);

        // color graph using the greedy algorithm
        colorGraph(graph, vertexNumber);
    }
}