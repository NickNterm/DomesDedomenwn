//Group 1 Pavlos Anagnostou 5440 Nikolaos Ntermaris 5477

public class StringTrie {

    private static int R = 26; // number of different characters
    private static int N = 0; // number of words in trie
    private Node root; // root of trie

    /* trie node */
    private static class Node {
        private boolean mark; // true if a word ends in this node
        private int count; // number of occurences of word
        private Node[] next = new Node[R]; // links to R children
    }

    /* auxiliary class: stores a word s and its number of occurrences */
    private static class Item {
        private String s;
        private int count;
    }

    // returns true if trie contains string s
    public int contains(String s) {
        Node x = contains(root, s, 0);
        if (x == null) {
            return 0;
        }
        return x.count;
    }

    // search in the subtree of x for the substring of s starting at position d
    private Node contains(Node x, String s, int d) {
        if (x == null) {
            return null;
        }
        if (d == s.length()) {
            return x; // end of word
        }
        char c = s.charAt(d); // next character
        int j = (int) c - 'a'; // index of next character
        return contains(x.next[j], s, d + 1);
    }

    // insert string s in trie
    public void insert(String s) {
        root = insert(root, s, 0);
    }

    // insert into the subtree of x the substring of s starting at position d
    private Node insert(Node x, String s, int d) {
        if (x == null) {
            x = new Node();
            x.count = 0;
        }
        if (d == s.length()) {
            if (!x.mark) {
                x.mark = true;
                N++;
            }
            x.count++;
            return x;
        } // end of word
        char c = s.charAt(d); // next character
        int j = (int) c - 'a'; // index of next character
        x.next[j] = insert(x.next[j], s, d + 1);
        return x;
    }

    // delete string s from trie
    public void delete(String s) {
        root = delete(root, s, 0);
    }

    // delete from the subtree of x the substring of s starting at position d
    private Node delete(Node x, String s, int d) {
        if (x == null) {
            return null;
        }
        if (d == s.length()) {
            x.count--;
            if (x.count == 0) {
                x.mark = false; // end of word: erase mark
                N--;
            }
        } else {
            char c = s.charAt(d); // next character
            int j = (int) c - 'a'; // index of next character
            x.next[j] = delete(x.next[j], s, d + 1);
        }

        if (x.mark) {
            return x; // x is marked so it is not deleted
        }
        for (int j = 0; j < R; j++) {
            if (x.next[j] != null) {
                return x; // x has non-null child so it is not deleted
            }
        }
        return null;
    }

    // collect all the words stored in the trie
    public Iterable<String> words() {
        Queue<String> q = new Queue<String>(N); // stores the words of the trie
        collect(root, "", q);
        return q;
    }

    // store the words with prefix pre stored in the subtree of x
    private void collect(Node x, String pre, Queue<String> q) {
        if (x == null) {
            return;
        }
        if (x.mark) {
            q.put(pre); // x is marked so pre is a word stored in the trie
        }
        for (int j = 0; j < R; j++) {
            char c = (char) ('a' + j);
            collect(x.next[j], pre + c, q);
        }
    }

    public Iterable<Item> wordsItems() {
        Queue<Item> q = new Queue<Item>(N); // stores the words of the trie
        collectItem(root, "", q);
        return q;
    }

    private void collectItem(Node x, String pre, Queue<Item> q) {
        if (x == null) {
            return;
        }
        if (x.mark) {
            Item i = new Item();
            i.s = pre;
            i.count = x.count;
            q.put(i); // x is marked so pre is a word stored in the trie
        }
        for (int j = 0; j < R; j++) {
            char c = (char) ('a' + j);
            collectItem(x.next[j], pre + c, q);
        }
    }

    // collect all the words with prefix s stored in the trie
    public Iterable<Item> wordsWithPrefix(String s) {
        Queue<Item> q = new Queue<Item>(N); // stores the words of the trie together with their number of
                                            // occurrences
                                            // Here create the wordsItems so that we don't
                                            // have to get into the trie again and use the
                                            // contains method that takes more time
        Iterable<Item> allWords = wordsItems();
        for (Item w : allWords) {
            if (w.s.startsWith(s)) {
                q.put(w);
            }
        }

        return q;
    }

    // collect all the words with prefix s stored in the trie
    public int countWordsWithPrefix(String s) {
        Node x = root;

        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            int j = (int) c - 'a';
            x = x.next[j];
            if (x == null) {
                return 0;
            }
        }
        return collect(x, "", 0);
    }

    private int collect(Node x, String pre, int count) {
        if (x == null) {
            return 0;
        }
        if (x.mark) {
            count++;
        }
        for (int j = 0; j < R; j++) {
            char c = (char) ('a' + j);
            count += collect(x.next[j], pre + c, 0);
        }
        return count;
    }

    // collect all the words that match s stored in the trie
    public Iterable<Item> wordsThatMatch(String s) {
        Queue<Item> q = new Queue<Item>(N); // stores the words of the trie together with their number of
                                            // occurrences
                                            // again user the wordsItems so that we don't
                                            // have to get into the trie again and use the
                                            // contains method that takes more time
        Iterable<Item> allWords = wordsItems();
        for (Item w : allWords) {
            boolean shouldput = true;
            if (s.length() != w.s.length()) {
                continue;
            }
            for (int i = 0; i < s.length(); i++) {
                if (s.charAt(i) != '?' && s.charAt(i) != w.s.charAt(i)) {
                    shouldput = false;
                    break;
                }

            }
            if (shouldput) {
                // here we would have to use
                // the contains and spend time
                q.put(w);
            }
        }
        return q;
    }

    // find the word stored in the trie that is the longest prefix of s
    public String longestPrefixOf(String s) {
        while (s.length() > 0) {
            if (contains(s) > 0) {
                break;
            }
            // each time we remove the last character of s
            s = s.substring(0, s.length() - 1);
        }
        // so s is the longest prefix of s stored in the trie
        // return s
        return s;
    }

    // return the most frequent word
    public Item mostFrequent() {
        Item I = new Item();

        Iterable<Item> words = wordsItems();

        for (Item i : words) {
            if (i.count > I.count) {
                I = i;
            }
        }

        return I;
    }

    // return number of words stored in the trie
    public int size() {
        return N;
    }

    // print all the words stored in the trie
    public void printWords(Iterable<Item> S) {
        /* modify code to also print number of occurrences of each word */
        for (Item s : S) {
            System.out.println(s.s + " " + s.count);
        }
    }

    public static void main(String[] args) {
        System.out.println("\nTest String Trie\n");

        StringTrie T = new StringTrie();

        In.init();
        long startTime = System.currentTimeMillis();

        while (!In.empty()) {
            String s = In.getString();
            T.insert(s);
        }

        System.out.println("" + T.size() + " words");
        long endTime = System.currentTimeMillis();
        long totalTime = endTime - startTime;
        System.out.println("construction time = " + totalTime);
        System.out.println("memory KB = "
                + (double) (Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()) / 1024 + "\n");
        // T.printWords(T.words());

        String s = "astonished";
        System.out.println("\ncontains " + s + " = " + T.contains(s));

        s = "carol";
        System.out.println("contains " + s + " = " + T.contains(s));

        s = "pigeon";
        System.out.println("contains " + s + " = " + T.contains(s));

        s = "wondered";
        System.out.println("contains " + s + " = " + T.contains(s));

        s = "governmental";
        System.out.println("\nlongest prefix of " + s + " = " + T.longestPrefixOf(s));

        s = "caro";
        System.out.println("\nnumber of words with prefix " + s + " = " + T.countWordsWithPrefix(s));
        System.out.println("\nwords with prefix " + s + " : ");
        T.printWords(T.wordsWithPrefix(s));

        s = "sco";
        System.out.println("\nnumber of words with prefix " + s + " = " + T.countWordsWithPrefix(s));

        s = "sc????e";
        System.out.println("\nwords that match " + s + " : ");
        T.printWords(T.wordsThatMatch(s));

        Item I = T.mostFrequent();
        System.out.println("\nmost frequent word = " + I.s + " " + I.count);

        T.delete("carol");
        T.delete("carouse");
        s = "caro";
        System.out.println("\nnumber of words with prefix " + s + " = " + T.countWordsWithPrefix(s));
        System.out.println("\nwords with prefix " + s + " : ");
        T.printWords(T.wordsWithPrefix(s));
        endTime = System.currentTimeMillis();
        totalTime = endTime - startTime;
        System.out.println("total time = " + totalTime);
    }
}
