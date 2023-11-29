//Group 1 Pavlos Anagnostou 5440 Nikolaos Ntermaris 5477
import java.util.Iterator;

// linked list implementation of a collection of items
public class Collection<Item> implements Iterable<Item> {

    private Node head; // first node in the list

    private class Node {
        Item item;
        Node next;
        Node(Item item, Node next) {
            this.item = item;
            this.next = next;
        }
    }

    Collection() {
        head = null;
    }

    public boolean isEmpty() {
        return head == null;
    }

    public void insert(Item item) {
        head = new Node(item, head);
    }
    
    public void add(Item item) {
        insert(item);
    }

    @Override
    public Iterator<Item> iterator()
    {
        return new CollectionIterator();
    }
    private class CollectionIterator implements Iterator<Item>
    {
        private Node current = head;

        @Override
        public boolean hasNext()
        {
            return current!=null;
        }

        @Override
        public void remove() {}
        
        @Override
        public Item next()
        {
            Item item = current.item;
            current = current.next;
            return item;
        }
    }

    public static void main(String[] args)
    {
        Collection<Integer> C = new Collection<Integer>();

        C.insert(1); C.insert(4); C.insert(2);

        for (Integer k : C) {
            System.out.println(""+k);
        }
    }
}
