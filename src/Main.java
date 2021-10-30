import GUI.Clicks;
import LCA.LCAsolver;
import LCA.MapGenerator;
import org.graphstream.graph.*;
import org.graphstream.graph.implementations.SingleGraph;

public class Main {
    public static void main(String args[]) {
        int n = 520;
        MapGenerator mapGenerator = new MapGenerator(n);
        int[][] adjList = mapGenerator.getAdjList();

        Graph graph = new SingleGraph("LCA Visualization");
        graph.setStrict(false);
        graph.setAutoCreate(true);
        for (int[] vertices : adjList) {
            graph.addEdge(vertices[0] + "_" + vertices[1], Integer.toString(vertices[0]), Integer.toString(vertices[1]));
        }
        System.out.println(graph.getNodeSet().size());
        LCAsolver lcAsolver = new LCAsolver(adjList);
        Clicks display=new Clicks(graph, lcAsolver);
    }
}
