
import java.io.*;

public class StringTrie {

    private static int R = 26;  // number of different characters
    private static int N = 0;   // number of words in trie
    private Node root;  // root of trie

    /* trie node */
    private static class Node {
        private boolean mark;  // true if a word ends in this node
        private int count;     // number of occurences of word
        private Node[] next = new Node[R];  // links to R children
    }

    /* auxiliary class: stores a word s and its number of occurrences */
    private static class Item {
         private String s;
         private int count;
    }
    
    // returns true if trie contains string s
    public boolean contains(String s) {
        Node x = contains(root, s, 0);
        if (x == null) {
            return false;
        } else {
            return x.mark;
        }
    }

    // search in the subtree of x for the substring of s starting at position d
    private Node contains(Node x, String s, int d) { 
        if (x == null) {
            return null;
        }
        if (d == s.length()) {
            return x;  // end of word
        }
        char c = s.charAt(d);   // next character
        int j = (int) c - 'a';  // index of next character
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
        }
        if (d == s.length()) {
            if (!x.mark) {x.mark = true; N++;}
            return x;
        }  // end of word
        char c = s.charAt(d);   // next character
        int j = (int) c - 'a';  // index of next character
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
            x.mark = false;  //  end of word: erase mark
            N--;
        } else {
            char c = s.charAt(d);   // next character
            int j = (int) c - 'a';  // index of next character
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

    // collect all the words with prefix s stored in the trie
    public Iterable<String> wordsWithPrefix(String s) {
        Queue<String> q = new Queue<String>(N); // stores the words of the trie together with their number of occurrences

        /* enter your code */

        return q;
    }

    // collect all the words with prefix s stored in the trie
    public int countWordsWithPrefix(String s) {

        int count = 0;
        
        /* enter your code */

        return count;
    }
    
    // collect all the words that match s stored in the trie
    public Iterable<String> wordsThatMatch(String s) {
        Queue<String> q = new Queue<String>(N); // stores the words of the trie together with their number of occurrences

        /* enter your code */

        return q;
    }

    // find the word stored in the trie that is the longest prefix of s 
    public String longestPrefixOf(String s) {
        int length = 0; // length of longest prefix

        /* enter your code */

        return s.substring(0, length);
    }
    
    // return the most frequent word
    public Item mostFrequent() {
        Item I = new Item();
        
        /* enter your code */
        
        return I;
    }
   
    // return number of words stored in the trie
    public int size() {
        return N;
    }

    // print all the words stored in the trie
    public void printWords(Iterable<String> S) {
        /* modify code to also print number of occurrences of each word */
        for (String s : S) {
            System.out.println(s); 
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
        System.out.println("memory KB = " + (double) (Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()) / 1024 + "\n");
        //T.printWords(T.words());
        
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
    }
}
