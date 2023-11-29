//Group 1 Pavlos Anagnostou 5440 Nikolaos Ntermaris 5477
import java.util.Random;


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
      // insert the item on the last position
      // the index is the last one
      index[j] = ++N;
      // the item is on the last position
      pq[N] = j;
      // the key of the item is the given key
      keys[j] = key;
      // the fix up to get in the right position
      fixUp(N);
    }
    // delete and return item with minimum key
    public int delMin() {
      // exchange the first item with the last one
      exchange(1,N--);
      // fix down the first item to get in the right position
      fixDown(1);
      // then cause we N-- the last item
      // that we removed is the N+1 position
      // (we don't have to use temp var)
      return pq[N+1];
    }

    // return item with minimum key
    public int minItem() {
      // this is just the root we know that
      return pq[1];
    }


    public void change(int j, Key key) {
      // change the key of the item
      keys[j] = key;
      // fix up and fix down to get in the right position
      fixUp(index[j]);
      fixDown(index[j]);
    }

    public void exchange(int i, int j) {
      // exchange the items
      int temp = pq[i];
      // set the pq
      pq[i] = pq[j];
      pq[j] = temp;
      int temp2 = index[pq[i]];
      // set the index
      index[pq[i]] = index[pq[j]];
      index[pq[j]] = temp2;
    }

    public void fixDown(int k) {
      // while we have children
      while(2*k<=N) {
        // get the children
        int j = 2*k;
        // get the smallest child
        if(j<N && keys[pq[j]].compareTo(keys[pq[j+1]])>0) j++;
        // if the parent is smaller than the smallest child
        // then break
        if(keys[pq[k]].compareTo(keys[pq[j]])<=0) break;
        // else exchange the parent with the smallest child
        exchange(k,j);
        // and go to the smallest child
        k = j;
      }
    }

    public void fixUp(int k) {
      // while we are not in the root
      // and the parent is bigger than the child
      while(k>1 && keys[pq[k/2]].compareTo(keys[pq[k]])>0) {
        // exchange the parent with the child
        exchange(k,k/2);
        // and go to the parent
        k = k/2;
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
