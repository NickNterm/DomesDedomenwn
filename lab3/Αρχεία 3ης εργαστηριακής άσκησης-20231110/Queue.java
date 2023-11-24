
class Queue<Item> {

    private class Node {

        Item item;
        Node next;

        Node(Item item) {
            this.item = item;
            next = null;
        }
    }
    private Node head, tail;
    private int N;

    Queue() {
        head = null;
        tail = null;
        N = 0;
    }

    boolean isEmpty() {
        return (head == null);
    }

    void put(Item item) {
        Node t = tail;
        tail = new Node(item);
        if (isEmpty()) {
            head = tail;
        } else {
            t.next = tail;
        }
        N++;
    }

    Item get() {
        Item item = head.item;
        Node t = head.next;
        head = t;
        N--;
        return item;
    }

    int size() {
        return N;
    }
}
