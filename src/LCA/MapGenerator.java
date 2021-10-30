package LCA;

import java.util.Random;

public  class MapGenerator {

    private  int[][] adjList;
    private int N;
    private int index=0;
    public MapGenerator(int N){
        this.N=N;
        adjList=new int[N-1][2];
        generateRandomTree();
    }
    // Prints edges of tree 
    // represented by give Prufer code 
    private void printTreeEdges(int prufer[], int m)
    {
        int vertices = m + 2;
        int vertex_set[] = new int[vertices];

        // Initialize the array of vertices 
        for (int i = 0; i < vertices; i++)
            vertex_set[i] = 0;

        // Number of occurrences of vertex in code 
        for (int i = 0; i < vertices - 2; i++)
            vertex_set[prufer[i] - 1] += 1;

        //System.out.print("\nThe edge set E(G) is:\n");

        int j = 0;

        // Find the smallest label not present in 
        // prufer[]. 
        for (int i = 0; i < vertices - 2; i++) {
            for (j = 0; j < vertices; j++) {

                // If j+1 is not present in prufer set 
                if (vertex_set[j] == 0) {

                    // Remove from Prufer set and print 
                    // pair. 
                    vertex_set[j] = -1;
                   //System.out.print("(" + (j) + ", "
                            //+ (prufer[i]-1) + ") ");
                    adjList[index][0]=j;
                    adjList[index][1]=prufer[i]-1;
                    index++;
                    vertex_set[prufer[i] - 1]--;

                    break;
                }
            }
        }

        j = 0;

        // For the last element 
        for (int i = 0; i < vertices; i++) {
            if (vertex_set[i] == 0 && j == 0) {

                //System.out.print("(" + (i) + ", ");
                adjList[index][0]=i;
                j++;
            }
            else if (vertex_set[i] == 0 && j == 1) {
                //System.out.print((i) + ")\n");
                adjList[index][1] = i;
                break;
            }
        }
    }

    // Function to Generate Random Tree 
    private void generateRandomTree()
    {
        Random rand = new Random();
        int length = N - 2;
        int[] arr = new int[length];

        // Loop to Generate Random Array 
        for (int i = 0; i < length; i++) {
            arr[i] = rand.nextInt(length + 1) + 1;
        }
        printTreeEdges(arr, length);
    }
    public int[][] getAdjList(){
        return  this.adjList;
    }
    // Driver Code 
    public static void main(String[] args)
    {
        int n = 128;
        MapGenerator mapGenerator=new MapGenerator(n);
    }
} 