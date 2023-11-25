public class DirectedEdge
{
    private final int v;          // edge source
    private final int w;          // edge target
    private final long weight;    // edge weight

    public DirectedEdge(int v, int w, long weight)
    {
        this.v = v;
        this.w = w;
        this.weight = weight;
    }

    public long weight()  // return edge weight
    {
        return weight;
    }

    public int from()  // return edge source
    {
        return v;
    }

    public int to()  // return edge target
    {
        return w;
    }
}
