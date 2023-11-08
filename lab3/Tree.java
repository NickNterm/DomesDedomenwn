
import java.io.*;

public class Tree {

    private final int N;              // number of tree nodes
    private int[] preorder;        // preorder numbering of nodes
    private int[] postorder;      // postorder numbering of nodes
    private int root;                 // root of the tree

    private Stack<Integer>[] children;  // children

    private Queue<Integer> preorderStack = new Queue<Integer>();
    private Queue<Integer> postorderStack = new Queue<Integer>();

    private int[] parent;
    // construct children lists from parent array
    public Tree(int N, int[] parent) {
        this.N = N;
        this.parent = parent;
        preorder = new int[N];
        postorder = new int[N];
        for (int i = 0; i < N; i++) {
            preorder[i] = -1;
            postorder[i] = -1;
        }

        children = new Stack[N];

        for(int j = 0; j<N; j++){
            children[j] = new Stack<Integer>();
            if(parent[j] == j){
                root = j;
            }
            for (int i = 0; i < N; i++){
                if(parent[i] == j && i != j){
                    // tote einai paidi toy
                    children[j].push(i);
                }
            }
        }
    }

    // traverse tree and store preorder and postorder numbering of the nodes
    public void traverse() {
        makeOrder(root);
        for(int i = 0; i<N; i++){
            preorder[i] = preorderStack.get();
            postorder[i] = postorderStack.get();
        }
    }

    public void makeOrder(int id){
        // put id in preorder cause it's always the 
        // first node to be visited
        preorderStack.put(id);
        // if the stack is empty then there are 
        // no children so we can put it in postorder
        if(children[id].isEmpty()){
            postorderStack.put(id);
            // also don't move on to the children
            return;
        }
        // else we have to visit the children
        int size = children[id].size();
        for(int i = 0; i < size; i++){
            // pop the children
            int child = children[id].pop();
            // and make order with that child
            makeOrder(child);
        }
        // also in the end when we have visited 
        // all the children we can 
        // put it in postorder
        postorderStack.put(id);
    }


    // test if v is an ancestor of w
    public boolean isAncestor(int v, int w) {
        boolean ans = false;
        // check the v preorder and postorder id
        int vIndexPre = -1;
        int vIndexPost = -1;
        // check the w preorder and postorder id
        int wIndexPre = -1;
        int wIndexPost = -1;
        for(int i = 0; i<N; i++){
            if(preorder[i] == v){
                vIndexPre = i;
            }
            if(postorder[i] == v){
                vIndexPost = i;
            }
            if(preorder[i] == w){
                wIndexPre = i;
            }
            if(postorder[i] == w){
                wIndexPost = i;
            }
        }
        // if something is missing say false
        if(vIndexPre == -1 || vIndexPost == -1 || wIndexPre == -1 || wIndexPost == -1){
            System.out.println("elousa");
            return false;
        }
        // then check the ids and if there
        // is a case that v is an ancestor of w
        // then v is ancestor and return true
        if(vIndexPre < wIndexPre && vIndexPost > wIndexPost){
            ans = true;
        }
        return ans;
    }

    // return the path from v to w in the tree  
    public Queue<Integer> treePath(int v, int w) {
        Queue<Integer> Q = new Queue<Integer>();
        Stack<Integer> S = new Stack<Integer>();
        // select to node pou einai pio katw aristera. dld me to mikrotero postorder
        while((!isAncestor(v, w) && !isAncestor(w, v)) || w == v){
            Q.put(v);
            v = parent[v];
        }

        int prev = -1;
        int prew = -1;
        for(int i = 0; i<N; i++){
            if(preorder[i] == v){
                prev = i;
            }
            if(preorder[i] == w){
                prew = i;
            }
        }
        while(v != w){
            if(prev > prew){
                S.push(v);
                v = parent[v];
            }else{
                S.push(w);
                w = parent[w];
            }
        }
        S.push(w);

        int size = S.size();
        for(int i = 0; i < size; i++){
            Q.put(S.pop());
        }
        return Q;
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
