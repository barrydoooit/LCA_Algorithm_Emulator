package LCA;

import RMQ.RMQsolver;

import java.util.ArrayList;

public class LCAsolver {
    private ArrayList<ArrayList<Integer>> adjList = new ArrayList<>();  // Adjacency list representation of tree
    private int vis[] ;   // Visited array to keep track visited nodes on tour
    // Array to store Euler Tour
    private int Euler[] ;
    private int EulerDepth[];
    private int EulerIndex=0;
    private RMQsolver rmQsolver;
    private int calculateCounter=0;
    private ArrayList<Double> RMQ_TimeUsed = new ArrayList<>();
    private ArrayList<Double> BF_TimeUsed = new ArrayList<>();
    public LCAsolver(int[][] treeMap){
        setAdjList(treeMap);
        long startTime = System.nanoTime();
        vis=new int[treeMap.length+1];
        Euler=new int[2*(treeMap.length+1)-1];
        EulerDepth=new int[2*(treeMap.length+1)-1];
        eulerTree(0, 0);
        rmQsolver=new RMQsolver(EulerDepth);
        long endTime = System.nanoTime();
        RMQ_TimeUsed.add( (double) (endTime - startTime) / 1000000);
    }
    //generate adjacency table
    private void setAdjList(int[][] treeMap){
        for(int i = 0; i <= treeMap.length; i++)
            adjList.add(new ArrayList<>());
        for (int[] ints : treeMap) {
            adjList.get(ints[0]).add(ints[1]);
            adjList.get(ints[1]).add(ints[0]);
        }
    }
    // Function to store Euler Tour of tree
    private void eulerTree(int u, int depth) {
        vis[u] = 1;
        Euler[EulerIndex] = u;
        EulerDepth[EulerIndex++]=depth;
        for(int it : adjList.get(u)) {
            if (vis[it] == 0) {
                eulerTree(it, depth+1);
                Euler[EulerIndex] = u;
                EulerDepth[EulerIndex++]=depth;
            }
        }
    }

    // Function to print Euler Tour of tree
    public void printEulerTour() {
        for(int i = 0; i < Euler.length; i++)
            System.out.print(Euler[i] + ", ");
        System.out.print("\n");
        for(int i = 0; i < Euler.length; i++)
            System.out.print(EulerDepth[i] + ", ");
        System.out.print("\n");
    }
    public int LCA(int nodeA, int nodeB){
        long startTime = System.nanoTime();
        int i=0,j=0;
        for(;;i++){
            if(Euler[i]==nodeA)
                break;
        }
        for(;;j++){
            if(Euler[j]==nodeB)
                break;
        }
        int result = Euler[rmQsolver.RMQ(i,j)];
        long endTime = System.nanoTime();
        RMQ_TimeUsed.add( (double) (endTime - startTime) / 1000000);
        return result;
    }

    public int bruteForceLCA(int nodeA, int nodeB){
        long startTime = System.nanoTime();
        vis=new int[vis.length];
        int result = haveElement(0, nodeA, nodeB);
        long endTime = System.nanoTime();
        BF_TimeUsed.add( (double) (endTime - startTime) / 1000000);
        return result;
    }
    private int haveElement(int node, int nodeA, int nodeB){
        if(node==nodeA || node==nodeB)
            return node;
        int elements=0;
        int from=0;
        vis[node]=1;
        for(int neighbors : adjList.get(node)) {
            if (vis[neighbors] == 0) {
                int temp=0;
                if((temp=haveElement(neighbors, nodeA,  nodeB))>0) {
                    from = temp;
                    elements++;
                }
            }
        }
        if(elements==0)
            return -1;
        if(elements==1)
            return from;
        if(elements==2)
            return node;
        else
            throw new RuntimeException("The queried nodes  have about "+elements+" duplicates!");
    }

    public void printTime(){
        System.out.printf("%-10s\n", "RMQ");
        for(int i=1; i<RMQ_TimeUsed.size();i++)
            System.out.printf("%-10f", RMQ_TimeUsed.get(i));
        System.out.print("\n");
        System.out.printf("%-10s\n", "BF");
        for(int i=0; i<BF_TimeUsed.size();i++)
            System.out.printf("%-10f", BF_TimeUsed.get(i));
        System.out.print("\n");
    }

    public static void main(String[] args) {
        int n=520;
        MapGenerator mapGenerator=new MapGenerator(n);
        LCAsolver lcAsolver= new LCAsolver(mapGenerator.getAdjList());

        for(int i=0;i<n;i++)
            for(int j=0;j<n;j++){
                if(lcAsolver.LCA(i,j)!=lcAsolver.bruteForceLCA(i,j)){
                    throw new RuntimeException("Computational Error!");
                }
            }
        System.out.println("Result: "+lcAsolver.LCA(2,27));
        System.out.println("Brute Result: "+lcAsolver.bruteForceLCA(2,27));
    }
}//0 14 17 2 13 21 13 22 8 19 8 22 13 23 13 2 27 18 27 2 7 1 20 1 9 24 9 25 9 16 4 16 15 10 15 16 11 28 11 30 26 30 11 16 12 6 12 3 29 5 29 3 12 31 12 16 9 1 7 2 17 14 0
