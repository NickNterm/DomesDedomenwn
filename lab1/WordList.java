// Group 1
// Nikolas Ntermaris AM 5477
// Paulos Anagnostou AM 5440

import java.io.*;

public class WordList {

    private class Node {
        String str;
        int count; // occurrences of str in input file
        Node next; // next node in doubly linked list
        Node previous; // previsou node in doubly linked list

        Node(String str) {
            this.str = str;
            this.next = null;
            this.previous = null;
            this.count = 1;
        }
    }

    private Node first; // first node of the doubly linked list
    private int nodeCount; // number of nodes

    public WordList() {
        this.nodeCount = 0;
    }

    int nodeCount() {
        return nodeCount;
    }

    // return the number of occurrences of word s in the input file
    public int contains(String s) {
        Node currentNode = first;
        // search through the linked list
        // set currentNode to next and if there is no next
        // the loop breaks
        while (currentNode != null) {
            if (currentNode.str.equals(s)) {
                return currentNode.count;
            }
            currentNode = currentNode.next;
        }
        // if there is no current node found, return 0
        return 0;
    }

    // add one more occurrence of word s; insert new node if s is not in the doubly
    // linked list
    // the list should be adjusted so that the words appear in decreasing number of
    // occurrences
    public void insert(String s) {
        // if the first node is null
        // Then there are no nodes created yet.
        // so this is the first node created
        if (first == null) {
            first = new Node(s);
            nodeCount++;
            return;
        }
        // in this case we know first node exists
        Node currentNode = first;
        // we search through the linked list with the for loop
        for (int i = 0; i < nodeCount; i++) {
            // we check if we find the node we are looking for
            if (currentNode.str.equals(s)) {
                // inc the counter of the node we are at
                currentNode.count++;
                // create a temp bool for while loop
                boolean flag = true;
                while (flag) {
                    // if there is no prev node we are first and no need
                    // to change the order
                    if (currentNode.previous == null) {
                        flag = false;
                        break;
                    }
                    // if the prev node has greater counter
                    // then there is no reason to change the order
                    if (currentNode.previous.count >= currentNode.count) {
                        flag = false;
                        break;
                    }
                    // We are going to swap the contents of the nodes
                    // Not the connections.
                    // storing a temp data for prev node
                    String prevstr = currentNode.previous.str;
                    int prevcount = currentNode.previous.count;
                    // set the prev node data to current node data
                    currentNode.previous.str = currentNode.str;
                    currentNode.previous.count = currentNode.count;
                    // set the current node data to prev node data
                    currentNode.str = prevstr;
                    currentNode.count = prevcount;
                    // also to keep the loop going we move to the prev
                    // node. The prev node due to the swap is now
                    // the old current node
                    currentNode = currentNode.previous;
                }
                // the swapping is done and now insertion is done
                return;
            }
            // in case there are no next Node then we hit the last one
            // So the node doesn't exist and we should create it
            // we don't have to sort it cause it's a new node
            if (currentNode.next == null) {
                // create the node
                Node newNode = new Node(s);
                // set the links
                currentNode.next = newNode;
                newNode.previous = currentNode;
                // inc counter
                nodeCount++;
                // leave the loop
                return;
            }
            // to keep the loop going we move to the next node
            currentNode = currentNode.next;
        }
    }

    // delete word s from the doubly linked list
    public void delete(String s) {
        Node currentNode = first;
        // loop through all the nodes
        for (int i = 0; i < nodeCount; i++) {
            // find the node we are looking for
            if (currentNode.str.equals(s)) {
                // change the links to delete a node
                // store prev, next node
                Node previousNode = currentNode.previous;
                Node nextNode = currentNode.next;
                // if there is a prev node then change the next link
                if (previousNode != null) {
                    if (previousNode.next != null) {
                        previousNode.next = nextNode;
                    }
                }
                // if there is a next node change the prev link
                if (nextNode != null) {
                    if (nextNode.previous != null) {
                        nextNode.previous = previousNode;
                    }
                }
                // decrement the counter
                nodeCount--;
                break;
            }
            // keep the loop going
            currentNode = currentNode.next;
        }
    }

    // sort doubly linked list in lexicographic order of words
    public void lexOrder() {
        // loop through all the nodes
        for (int i = 0; i < nodeCount; i++) {
            Node currentNode = first;
            // the loop for all the next nodes in this list
            for (int j = 0; j < nodeCount - i - 1; j++) {
                // compare the strings
                if (currentNode.str.compareTo(currentNode.next.str) > 0) {
                    // store the old data
                    String tempStr = currentNode.str;
                    int tempCount = currentNode.count;
                    // set the current data to next data
                    currentNode.str = currentNode.next.str;
                    currentNode.count = currentNode.next.count;
                    // set the next data to current data
                    currentNode.next.str = tempStr;
                    currentNode.next.count = tempCount;
                }
                // move to the next node to keep the loop going
                currentNode = currentNode.next;
            }
        }

    }

    // find the k-th word in the doubly linked list
    public String select(int k) {
        // if k is larger than the number of nodes, return null
        if (k > nodeCount) {
            return null;
        }

        Node currentNode = first;
        // loop through the nodes for k nodes
        for (int i = 1; i < k; i++) {
            currentNode = currentNode.next;
        }
        // return the k-th one
        return currentNode.str;
    }

    // print first k strings of the doubly linked list
    public void print(int k) {
        // if the k is larger print the whole list
        if (k > nodeCount) {
            k = nodeCount;
        }

        Node currentNode = first;
        // loop through the nodes
        for (int i = 0; i < k; i++) {
            // print the node
            System.out.println("\t" + currentNode.str + "(" + currentNode.count + ")");
            currentNode = currentNode.next;
        }
    }

    // do not modify main
    public static void main(String[] args) {
        System.out.println("Test WordList");

        WordList L = new WordList();

        In.init();
        long startTime = System.currentTimeMillis();
        while (!In.empty()) {
            String s = In.getString();
            if (s.isEmpty())
                continue;
            L.insert(s);
        }
        long endTime = System.currentTimeMillis();
        long listTime = endTime - startTime;
        System.out.println("linked list construction time = " + listTime);
        System.out.println("number of linked list nodes = " + L.nodeCount());
        System.out.println("");

        System.out.println("contains 'and' " + L.contains("and") + " times");
        System.out.println("contains 'astonished' " + L.contains("astonished") + " times");
        System.out.println("contains 'boat' " + L.contains("boat") + " times");
        System.out.println("contains 'path' " + L.contains("path") + " times");
        System.out.println("contains 'the' " + L.contains("the") + " times");
        System.out.println("contains 'train' " + L.contains("train") + " times");
        System.out.println("contains 'tom' " + L.contains("tom") + " times");
        System.out.println("contains 'wondered' " + L.contains("wondered") + " times");
        System.out.println("");

        System.out.println("\nthe 10 most frequent words are:");
        L.print(10);

        String s = L.select(9);
        System.out.println("deleting '" + s + "'");
        L.delete(s);

        s = L.select(8);
        System.out.println("deleting '" + s + "'");
        L.delete(s);

        s = L.select(7);
        System.out.println("deleting '" + s + "'");
        L.delete(s);

        System.out.println("\nthe remaining 10 most frequent words are:");
        L.print(10);

        System.out.println("\nsorting words in lexicographical order");
        L.lexOrder();
        System.out.println("first 10 words in lexicographical order are:");
        L.print(10);

        endTime = System.currentTimeMillis();
        long totalTime = endTime - startTime;
        System.out.println("\ntotal running time = " + totalTime);
    }
}
