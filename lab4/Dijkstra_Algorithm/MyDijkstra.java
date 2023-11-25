
// compute shortest paths from a source vertex s
public class MyDijkstra
{
    private static DirectedEdge[] edgeTo;        // last edge in shortest path
    private static long[] distTo;                // distance from source
    //private static NaiveIndexMinPQ<Long> PQ;
    private static IndexMinPQ<Long> PQ;

    private static long globalDiam;

    // update distances of neighbours of node v
    private static void relax(EdgeWeightedDigraph G, int v) {
        for (DirectedEdge e : G.adj(v)) {
            int w = e.to();
            if (distTo[w] > distTo[v] + e.weight()) {
                distTo[w] = distTo[v] + e.weight();
                edgeTo[w] = e;
                if (PQ.contains(w)){
                  PQ.change(w,distTo[w]);
                }
                else {
                  if (distTo[w] > globalDiam){
                      globalDiam = distTo[w];
                  }
                  PQ.insert(w,distTo[w]);
                }
            }
        }
    }

    public long getGlobalDiam(EdgeWeightedDigraph G, int s) {
        MyDijkstra(G, s);
        return globalDiam;
    }

    // Dijkstra's single source shorstest paths algorithm from source node s
    public static void MyDijkstra(EdgeWeightedDigraph G, int s)
    {
        int N = G.nodes();
        edgeTo = new DirectedEdge[N];
        distTo = new long[N];
        for (int i=0; i<N; i++) {
            distTo[i] = Long.MAX_VALUE;
        }
        distTo[s] = 0;

        //PQ = new NaiveIndexMinPQ<Long>(N);
        PQ = new IndexMinPQ<Long>(N);
        PQ.insert(s,distTo[s]);
        int v=-1;
        while (!PQ.isEmpty()) {
            v = PQ.delMin();
            relax(G,v);
        }
       
        //System.out.println("distance from " + s + " to " + N/2 + " = " + distTo[N/2]);
        //System.out.println("distance from " + s + " to " + (N-2) + " = " + distTo[N-2]);
    }
    
    
    // compute the diameter of graph G, i.e., the maximum distance between any two vertices
    public static long diameter(EdgeWeightedDigraph G)
    {
        long diam = 0;
        for (int i=0; i<G.nodes(); i++)
        {
            if (i%1000 == 0){
              System.out.println("computing diameter from node " + i);
            }
            MyDijkstra myalg = new MyDijkstra();
            long tempDiam = myalg.getGlobalDiam(G, i);
            if(tempDiam > diam){
              diam = tempDiam;
            }

        }
        return diam;
    }

    public static void main(String[] args)
    {
        In.init();
        int N = In.getInt();
        EdgeWeightedDigraph G = new EdgeWeightedDigraph(N+1);
        int M = In.getInt();
        for (int i=0; i<M; i++)
        {
            int v = In.getInt();
            int w = In.getInt();
            long weight = In.getLong();
            DirectedEdge e = new DirectedEdge(v,w,weight);
            G.addEdge(e);
        }
        
        long startTime = System.currentTimeMillis();
        
        MyDijkstra(G,1);
        
        long diam = diameter(G);
        System.out.println("diameter = " + diam);
        
        long endTime = System.currentTimeMillis();
        
        long totalTime = endTime - startTime;
        System.out.println("total time = " + totalTime);
    }
}
