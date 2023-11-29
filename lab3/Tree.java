//Group 1 Pavlos Anagnostou 5440 Nikolaos Ntermaris 5477

public class Tree {

    private final int N; // number of tree nodes
    private int[] preorder; // preorder numbering of nodes
    private int[] postorder; // postorder numbering of nodes
    private int root; // root of the tree

    private Stack<Integer>[] children; // children

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

        for (int j = 0; j < N; j++) {
            children[j] = new Stack<Integer>();
            if (parent[j] == j) {
                root = j;
            }
            for (int i = 0; i < N; i++) {
                if (parent[i] == j && i != j) {
                    children[j].push(i);
                }
            }
        }
    }

    // traverse tree and store preorder and postorder numbering of the nodes
    public void traverse() {
        makeOrder(root);
    }

    int preorderCount = 1;
    int postorderCount = 1;

    public void makeOrder(int id) {
        // preoorder counter is the number of the
        // node in the list that we search
        preorder[id] = preorderCount;
        preorderCount++;
        // for loop in the children of the node
        int size = children[id].size();
        for (int i = 0; i < size; i++) {
            // pop a childer and make order of this
            makeOrder(children[id].pop());
        }
        // when it ends you can put the postorder number
        postorder[id] = postorderCount;
        postorderCount++;
    }

    // test if v is an ancestor of w
    public boolean isAncestor(int v, int w) {
        return preorder[v] <= preorder[w] && postorder[v] >= postorder[w];
    }

    // return the path from v to w in the tree
    public Queue<Integer> treePath(int v, int w) {
        Queue<Integer> Q = new Queue<Integer>();
        Stack<Integer> S = new Stack<Integer>();

        if (v == w) {
            Q.put(v);
            return Q;
        }

        while (!(isAncestor(v, w) || isAncestor(w, v))) {
            Q.put(v);
            v = parent[v];
        }

        // apo edw kai katw einai ancestors
        if (isAncestor(v, w)) {
            while (v != w) {
                S.push(w);
                w = parent[w];
            }
            S.push(v);
            int size = S.size();
            for (int i = 0; i < size; i++) {
                Q.put(S.pop());
            }
            return Q;
        }
        while (v != w) {
            Q.put(v);
            v = parent[v];
        }
        Q.put(v);
        return Q;
    }

    public void printQueue(Queue<Integer> Q) {
        while (!Q.isEmpty()) {
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
