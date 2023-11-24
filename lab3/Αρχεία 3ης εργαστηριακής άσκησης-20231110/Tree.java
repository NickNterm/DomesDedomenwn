
import java.io.*;

public class Tree {

    private final int N;              // number of tree nodes
    private int[] preorder;        // preorder numbering of nodes
    private int[] postorder;      // postorder numbering of nodes
    private int root;                 // root of the tree
    private Collection<Integer>[] children; // children of each node
    private int counter=1;// used in traverse method
    private int revcounter=N;// used in traverse method
    private Queue<Integer> Q = new Queue<Integer>();//used in treePath

    // construct children lists from parent array
    public Tree(int N, int[] parent) {
        this.N = N;
        preorder = new int[N];
        postorder = new int[N];
        children = (Collection<Integer>[]) new Collection[N];

        for (int i = 0; i < N; i++) {
            children[i] = new Collection<Integer>();
        }

        for (int i=0; i<N; i++){
            if(parent[i]!=i){
                children[parent[i]].insert(i);
            }else{
                root = i;
            }
        }
    }

    public Iterable<Integer> Children(int v) {
        return children[v];
    }

    // traverse tree and store preorder and postorder numbering of the nodes
    public void traverse() {
        makePreorder(root);
        makePostorder(root);
    }

    public void makePreorder(int root){
        preorder[root]=counter;
        ++counter;
        for (int i : Children(root)){
            makePreorder(i);
        }
    }

    public void makePostorder(int root){
        postorder[root]=revcounter;
        --revcounter;
        for (int i=children[root].length; i>0; i--){
            makePostorder(i);
        }
    }


    // test if v is an ancestor of w
    public boolean isAncestor(int v, int w) {
        boolean ans = false;
        if (preorder[v] < preorder[w] && postorder[v] > postorder[w]){
            ans = true;
        }
        return ans;
    }

    // return the path from v to w in the tree  
    public Queue<Integer> treePath(int v, int w) {
        Q.put(v);
        if (hasChildren(v)){
            for(int i: children[v]){
                if (i==w){
                    Q.put(i);
                    break;
                }
                treePath(i,w);
            }
        }else{Q.get();}
        return Q;
    }

    public boolean hasChildren(int v){
        return children[v].isEmpty();
    }

    public void printQueue(Queue<Integer> Q) {
        // remove comments if Queue is ready
        
        while ( !Q.isEmpty() ) {
            int x = Q.get();
            System.out.print(" " + x + " ");
        }
        System.out.println("");
         
    }

    public static void main(String[] args) {
        In.init();
        int n = In.getInt();

        int[] parent = new int[n];
        for (int i = 0; i < n; i++) {
            int v = In.getInt();
            parent[i] = v;
        }

        Tree T = (Tree) new Tree(n, parent);
        T.traverse();

        int w = n / 4;
        int u = n / 2;
        int v = n - 1;
        int z = 0;

        System.out.println("" + w + " ancestor of " + u + " ? = " + T.isAncestor(w, u));
        System.out.println("" + w + " ancestor of " + v + " ? = " + T.isAncestor(w, v));
        System.out.println("" + w + " ancestor of " + z + " ? = " + T.isAncestor(w, z));
        System.out.println("");

        System.out.println("" + u + " ancestor of " + w + " ? = " + T.isAncestor(u, w));
        System.out.println("" + u + " ancestor of " + v + " ? = " + T.isAncestor(u, v));
        System.out.println("" + u + " ancestor of " + z + " ? = " + T.isAncestor(u, z));
        System.out.println("");

        System.out.println("" + v + " ancestor of " + w + " ? = " + T.isAncestor(v, w));
        System.out.println("" + v + " ancestor of " + u + " ? = " + T.isAncestor(v, u));
        System.out.println("" + v + " ancestor of " + z + " ? = " + T.isAncestor(v, z));
        System.out.println("");

        System.out.println("" + z + " ancestor of " + w + " ? = " + T.isAncestor(z, w));
        System.out.println("" + z + " ancestor of " + u + " ? = " + T.isAncestor(z, u));
        System.out.println("" + z + " ancestor of " + v + " ? = " + T.isAncestor(z, v));
        System.out.println("");

        Queue<Integer> Q;
        int length;

        System.out.print("Path from " + w + " to " + u + " = ");
        Q = T.treePath(w, u);
        length = Q.size();
        T.printQueue(Q);
        System.out.println("Path length = " + length + "\n");

        System.out.print("Path from " + w + " to " + v + " = ");
        Q = T.treePath(w, v);
        length = Q.size();
        T.printQueue(Q);
        System.out.println("Path length = " + length + "\n");

        System.out.print("Path from " + u + " to " + v + " = ");
        Q = T.treePath(u, v);
        length = Q.size();
        T.printQueue(Q);
        System.out.println("Path length = " + length + "\n");

        System.out.print("Path from " + z + " to " + w + " = ");
        Q = T.treePath(z, w);
        length = Q.size();
        T.printQueue(Q);
        System.out.println("Path length = " + length + "\n");

        System.out.print("Path from " + z + " to " + u + " = ");
        Q = T.treePath(z, u);
        length = Q.size();
        T.printQueue(Q);
        System.out.println("Path length = " + length + "\n");

        System.out.print("Path from " + z + " to " + v + " = ");
        Q = T.treePath(z, v);
        length = Q.size();
        T.printQueue(Q);
        System.out.println("Path length = " + length + "\n");
    }
}
