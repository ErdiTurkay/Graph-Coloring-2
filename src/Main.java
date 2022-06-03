import java.io.*;
import java.util.*;

class Main{

    public static void colorGraph(Graph graph, int n)
    {
        // Keep track of the color assigned to each vertex.
        Map<Integer, Integer> result = new HashMap<>();

        String colors = "";
        int optimal = 0;

        // Assign a color to vertex one by one.
        for (int i = 0; i < n; i++)
        {
            // Set to store the color of adjacent vertices of 'i'
            Set<Integer> assigned = new TreeSet<>();

            // Controls the colors of the vertices adjacent to i and stores these values.
            for (int x: graph.adjList.get(i))
            {
                if (result.containsKey(x)) {
                    assigned.add(result.get(x));
                }
            }

            // Controls the lowest possible color value.
            int color = 0;
            for (Integer c: assigned)
            {
                if (color != c) {
                    break;
                }
                color++;
            }

            // Assigns the lowest color value it finds to vertex.
            result.put(i, color);
        }

        for (int i = 0; i < n; i++)
        {
            // Writes the color value of all vertexes side by side.
            colors += result.get(i) + " ";

            // The 1 upper value of the highest color value gives the optimal value. (Because 0 is included.)
            // Therefore, it checks whether the value found in each step is the maximum value.
            if(result.get(i)>optimal)
                optimal = result.get(i)+1;
        }

        // It writes the optimal value it finds and the color values of all vertexes.
        System.out.println(optimal);
        System.out.println(colors);

        try{
            File dosya = new File("output.txt");
            FileWriter yazici = new FileWriter(dosya);
            BufferedWriter yaz = new BufferedWriter(yazici);
            yaz.write(optimal + "\n" + colors);
            yaz.close();
            System.out.println("\n[✓] Output file is created according to the specified input file.");
        }
        catch(Exception hata){
            hata.printStackTrace();
        }
    }

    public static void main(String[] args)
    {
        Scanner scn = new Scanner(System.in);

        while(true){
            System.out.print("Enter the file name to be read. (including .txt): ");
            String input = scn.nextLine();
            File file = new File(input);
            int vertexCount = 0;
            ArrayList edgeList = new ArrayList();

            // If the file whose name is entered is not located in the current location,
            // a check is made to give an error message.
            if(file.exists() && !file.isDirectory()) {
                try {
                    try (BufferedReader br = new BufferedReader(new FileReader(file))) {
                        String line;
                        while ((line = br.readLine()) != null) {
                            // Splits the line by spaces.
                            String[] columns = line.split(" ");

                            // If the line starts with p, this line gives us the vertex amount.
                            if(columns[0].equals("p")) {
                                vertexCount = Integer.parseInt(columns[1]);
                                break;
                            }
                        }
                    }

                    try (BufferedReader br = new BufferedReader(new FileReader(file))) {
                        String line;
                        while ((line = br.readLine()) != null) {
                            // Splits the line by spaces.
                            String[] columns = line.split(" ");

                            // If the line starts with e, this line tells us the edges. (from-to) also (to-from)
                            if(columns[0].equals("e")) {
                                // Creates an Edge object and adds it to the list.
                                Edge newEdge = new Edge(Integer.parseInt(columns[1])-1, Integer.parseInt(columns[2])-1);
                                edgeList.add(newEdge);
                            }
                        }
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }

                // Create a Graph object based on the given edges and vertex count.
                Graph graph = new Graph(edgeList, vertexCount);

                // Colors the graph according to the given graph object and vertex count.
                colorGraph(graph, vertexCount);

                break;
            }

            else{
                System.out.println("[!] The file you are looking for was not found, please try again.\n");
            }
        }
    }
}