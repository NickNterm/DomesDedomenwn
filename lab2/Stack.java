import java.io.*;

// implementation of a pushdown stack
public class Stack<Item> {
    class Node {
        Item item;
        Node next;
    }

    private Node first = null;

    public boolean isEmpty() {
        return first == null;
    }

    public void push(Item item) {
        // add item to top of stack
        Node oldfirst = first;
        first = new Node();
        first.item = item;
        first.next = oldfirst;
    }

    public Item pop() {
        // remove item from top of stack
        Item item = first.item;
        first = first.next;
        return item;
    }
}
