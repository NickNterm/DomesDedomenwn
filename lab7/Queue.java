import java.util.Iterator;

// array implementation of a queue
public class Queue<Item> implements Iterable<Item> {

    private Item[] A;
    private int N, head, tail;

    Queue(int maxN) {
         A = (Item[]) new Object[maxN+1];
         N = maxN+1;
         head = N;
         tail = 0;
    }

    boolean isEmpty() {
        return ( head % N == tail );
    }

    void put(Item item) {
        A[tail++] = item;
        tail = tail % N;
    }

    Item get() {
        head = head % N;
        return A[head++];
    }

    public Iterator<Item> iterator()
    {
        return new ListIterator();
    }
    private class ListIterator implements Iterator<Item>
    {
        private int current = head;

        public boolean hasNext()
        {
            return ( ( current % N ) != tail );
        }

        public void remove() {}

        public Item next()
        {
            Item item = A[(current %N)];
            current++;
            return item;
        }
    }
}
