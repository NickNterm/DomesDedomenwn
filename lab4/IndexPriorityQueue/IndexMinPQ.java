import java.io.*;

// minimum index priority queue implemented with a binary heap
public class IndexMinPQ<Key extends Comparable<Key>>
{
    private int N;           // number of items on priority queue
    private int   pq[];      // binary heap of items with respect to their keys
    private int   index[];   // index[j] = position of item j in pq : pq[index[j]]=index[pq[j]]=j
    private Key[] keys;      // key[j]   = priority of item j

    public IndexMinPQ(int maxN)
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

    // return the key of item j
    public Key getKey(int j) {
        return keys[j];
    }

    // insert item j with a key
    public void insert(int j, Key key) {
      index[j] = ++N;
      pq[N] = j;
      keys[j] = key;
      fixUp(N);
    }

   // public void change(int j, Key key){
   //   pq[j] = key.getKey();

   // }
    // delete and return item with minimum key
    public int delMin() {
      int min = pq[1];
      int temp = pq[1];
      pq[1] = pq[N];
      pq[N] = temp;
      N--;
      fixDown(1);
      return min;
    }

    // return item with minimum key
    public int minItem() {
      return pq[1];
    }

    public void fixUp(int k) {
      while(k>1 && keys[pq[k/2]].compareTo(keys[pq[k]])>0) {
        int temp = pq[k];
        pq[k] = pq[k/2];
        pq[k/2] = temp;
        k = k/2;
      }
    }

    public void fixDown(int k) {
      while(2*k<=N) {
        int j = 2*k;
        if(j<N && keys[pq[j]].compareTo(keys[pq[j+1]])>0) j++;
        if(keys[pq[k]].compareTo(keys[pq[j]])<=0) break;
        int temp = pq[k];
        pq[k] = pq[j];
        pq[j] = temp;
        k = j;
      }
    }

    // print pq, key and index arrays
    public void printPQ()
    {
        for (int j=1; j<=N; j++)
            System.out.println("pq["+j+"]= " + pq[j] + ", key= " + keys[pq[j]]);

        for (int j=0; j<N; j++)
            System.out.println("index["+j+"]= " + index[j]);
    }

    public static void main(String[] args) {
        System.out.println("Test Index Min Priority Queue");

        int N = Integer.parseInt(args[0]);
        System.out.println("Number of items to be inserted = " + N);

        long startTime = System.currentTimeMillis();
        IndexMinPQ<Integer> PQ = new IndexMinPQ<Integer>(N);
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
        }
        long endTime = System.currentTimeMillis();
        long totalTime = endTime - startTime;
        System.out.println("total time = " + totalTime);
    }
}
