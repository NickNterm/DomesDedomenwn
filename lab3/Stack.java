//Group 1 Pavlos Anagnostou 5440 Nikolaos Ntermaris 5477

import java.io.*;

// implementation of a pushdown stack
public class Stack<Item> {
    class Node {
        Item item;
        Node next;
    }

    private Node first = null;
    private int size = 0;

    public boolean isEmpty() {
        return first == null;
    }

    public void push(Item item) {
        // add item to top of stack
        Node oldfirst = first;
        first = new Node();
        first.item = item;
        first.next = oldfirst;
        size++;
    }

    public int size() {
        return size;
    }

    public Item pop() {
        // remove item from top of stack
        Item item = first.item;
        first = first.next;
        return item;
    }

    public boolean contains(Item item) {
        // check if item is in stack
        Node current = first;
        while (current != null) {
            if (current.item.equals(item)) {
                return true;
            }
            current = current.next;
        }
        return false;
    }
}
