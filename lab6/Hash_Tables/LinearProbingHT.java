import java.io.*;

public class LinearProbingHT<Key, Value> {

    private class Pair {
        Key key;
        Value value;

        Pair(Key key, Value value) {
            this.key = key;
            this.value = value;
        }
    }

    private int m; // hash table size
    private Pair[] T; // hash table
    private int n; // number of distinct items inserted
    private int loadFactorThreshold = 70;

    public int words() {
        return n;
    }

    // hash function
    private int hash(Key key) {
        return ((key.hashCode() & 0x7fffffff) % m);
    }

    // linear probing
    private int hashL(int k, int i) {
        return ((k + i) % m);
    }

    // constructor: initialize empty hash table of size M
    LinearProbingHT(int M) {
        m = M;
        n = 0;
        T = new LinearProbingHT.Pair[m];
    }

    public double loadFactor() {
        return (double) 100 * n / m;
    }

    // insert key with associated value
    public void insert(Key key, Value value) {
        int j = 0;
        for (int i = 0; i < m; i++) {
            j = hashL(hash(key), i);
            if (T[j] == null) {
                T[j] = new Pair(key, value);
                n++;
                if (loadFactor() > loadFactorThreshold) {
                    resize(2 * m);
                }
                break;
            } else if (T[j].key.equals(key)) {
                T[j].value = value;
                break;
            }
        }
    }

    // resize the hash table to have the given number of chains,
    private void resize(int chains) {
        LinearProbingHT<Key, Value> temp = new LinearProbingHT<Key, Value>(chains);
        for (int i = 0; i < m; i++) {
            if (T[i] != null) {
                temp.insert(T[i].key, T[i].value);
            }
        }
        m = temp.m;
        n = temp.n;
        T = temp.T;
    }

    // return the value associated with key
    public Value contains(Key key) {
        int j = 0;
        for (int i = 0; i < m; i++) {
            j = hashL(hash(key), i);
            if (T[j] == null) {
                return null;
            } else if (T[j].key.equals(key)) {
                return T[j].value;
            }
        }
        return null;
    }

    // print hash table
    void print() {
        for (int j = 0; j < m; j++) {
            if (T[j] == null) {
                System.out.println("T[" + j + "]=");
                continue;
            }
            System.out.println("T[" + j + "]=" + "(" + T[j].key + "," + T[j].value + ") ");
        }
    }

    public static void main(String[] args) {
        System.out.println("Test Hash Table with Linear Probing");

        int M = 3000; // initial hash table size
        LinearProbingHT T = new LinearProbingHT<String, Integer>(M);

        In.init();
        long startTime = System.currentTimeMillis();
        while (!In.empty()) {
            String s = In.getString();
            Integer count = (Integer) T.contains(s);
            if (count != null) {
                T.insert(s, count + 1);
            } else {
                T.insert(s, 1);
            }
        }
        // T.print();
        long endTime = System.currentTimeMillis();
        long chtTime = endTime - startTime;
        System.out.println("construction time = " + chtTime);
        System.out.println("load factor = " + T.loadFactor());

        System.out.println("number of words = " + T.words());

        System.out.println("contains 'and' " + T.contains("and") + " times");
        System.out.println("contains 'astonished' " + T.contains("astonished") + " times");
        System.out.println("contains 'boat' " + T.contains("boat") + " times");
        System.out.println("contains 'carol' " + T.contains("carol") + " times");
        System.out.println("contains 'city' " + T.contains("city") + " times");
        System.out.println("contains 'scrooge' " + T.contains("scrooge") + " times");
        System.out.println("contains 'the' " + T.contains("the") + " times");
        System.out.println("contains 'train' " + T.contains("train") + " times");
        System.out.println("contains 'wondered' " + T.contains("wondered") + " times");

        endTime = System.currentTimeMillis();
        long totalTime = endTime - startTime;
        System.out.println("total running time = " + totalTime);
    }
}
