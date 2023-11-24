import java.io.* ;

public class EdgeWeightedDigraph
{
    private final int N;                     // number of nodes
    private int M;                           // number of edges
    private Collection<DirectedEdge>[] adj;  // adjacency lists

    // construct an edge-weighted directed graph with N nodes
    public EdgeWeightedDigraph(int N)
    {
        this.N = N;
        this.M = 0;
        adj = (Collection<DirectedEdge>[]) new Collection[N];   // array of lists
        for (int i=0; i<N; i++)
            adj[i] = new Collection<DirectedEdge>();    // initialize lists to be empty
    }

    // return the number of nodes
    public int nodes()  
    {
        return N;
    }

    // return the number of edges
    public int edges()  
    {
        return M;
    }

    // add directed edge e
    public void addEdge(DirectedEdge e)
    {
        adj[e.from()].add(e);
        M++;
    }

    // edges (v,x) leaving node v
    public Iterable<DirectedEdge> adj(int v)
    {
        return adj[v];
    }

    public void printGraph()
    {
        System.out.println("adjacency lists; for each arc (u,v) with weight w it prints (v,w)");
        for (int v=0; v<N; v++)
        {
            System.out.print(v + " : ");
            for (DirectedEdge e : adj[v])
            {
                System.out.print("(" + e.to() + ", " + e.weight() + ") ");
            }
            System.out.println("");
        }
    }

    public static void main(String[] args)
    {
        In.init();
        int N = In.getInt();
        EdgeWeightedDigraph G = new EdgeWeightedDigraph(N);
        int M = In.getInt();
        for (int i=0; i<M; i++)
        {
            int v = In.getInt();
            int w = In.getInt();
            long weight = In.getLong();
            DirectedEdge e = new DirectedEdge(v,w,weight);
            G.addEdge(e);
        }
        G.printGraph();
    }
}
