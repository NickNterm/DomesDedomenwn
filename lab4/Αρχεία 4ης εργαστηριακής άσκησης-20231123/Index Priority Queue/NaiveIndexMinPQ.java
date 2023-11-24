import java.io.*;
import java.util.Random;

// minimum index priority queue implemented with an unsorted array
public class NaiveIndexMinPQ<Key extends Comparable<Key>>
{
    private int N;           // number of items on priority queue
    private int   pq[];      // array of indices
    private int   index[];   // index[j] = position of item j in pq : pq[index[j]]=index[pq[j]]=j
    private Key[] keys;      // key[j]   = priority of item j

    public NaiveIndexMinPQ(int maxN)
    {
        keys   = (Key []) new Comparable[maxN+1];
        pq     = new int[maxN+1];
        index  = new int[maxN+1];
        for (int i = 0; i <= maxN; i++) index[i]=-1;
    }

    // check if priority queue is empty
    public boolean isEmpty() {
        return N == 0;
    }

    // check if priority queue contains item j
    public boolean contains(int j) {
        return index[j] != -1;
    }

    // compare keys of items pq[i] and pq[j]
    private boolean less(int i, int j) {
        return keys[pq[i]].compareTo(keys[pq[j]]) < 0;
    }

    // exchange the position of items pq[i] and pq[j] in array pq[]
    private void exch(int i, int j) {
        int t=pq[i]; pq[i]=pq[j]; pq[j]=t;
        index[pq[i]]=i;
        index[pq[j]]=j;
    }

    // insert item j with a key
    public void insert(int j, Key key) {
        index[j] = N;
        pq[N] = j;
        keys[j] = key;
        N++;
    }

    // delete and return item with minimum key
    public int delMin()
    {
        int minKeyItem = minItem();
        int indexMin = index[minKeyItem]; // position of item with minimum key in array pq
        exch(indexMin,--N);
        keys[pq[N]]  = null;
        index[pq[N]] = -1;
        return minKeyItem;
    } 

    // return item with minimum key
    public int minItem()
    {
        int j, minIndex = 0;
        for (j = 1; j < N; j++)  if ( less(j,minIndex) ) minIndex = j;
        return pq[minIndex];
    }

    // return the key of item j
    public Key getKey(int j) {
        return keys[j];
    }

    // change the key of item j
    public void change(int j, Key key) {
        keys[j] = key;
    }

    public void printPQ()
    {
        for (int j=0; j<N; j++)
            System.out.println("pq["+j+"]= " + pq[j] + ", key= " + keys[pq[j]]);
    }

    public static void main(String[] args) {
        System.out.println("Test Naive Index Minimum Priority Queue");

        int N = Integer.parseInt(args[0]);
        System.out.println("Number of items to be inserted = " + N);

        long startTime = System.currentTimeMillis();
        NaiveIndexMinPQ<Integer> PQ = new NaiveIndexMinPQ<Integer>(N);
        Random rand = new Random(0);

        for (int i=0; i<N; i++) {
            int key = rand.nextInt(N*N); // assign random keys
            System.out.println("insert item " + i + " key " + key);
            PQ.insert(i,key);
        }
        PQ.printPQ();
        while ( !PQ.isEmpty() ) {
            int key = PQ.getKey(PQ.minItem());
            int k = PQ.delMin();
            System.out.println("delMin item " + k + " key " + key);
            //PQ.printPQ();
        }
        long endTime = System.currentTimeMillis();
        long totalTime = endTime - startTime;
        System.out.println("total time = " + totalTime);
    }
}
