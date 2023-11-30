import java.util.*;

public class TreeMapTest {

    public static void main(String[] args) {
        System.out.println("Test TreeMap");

        Map<Integer,String> TM = new TreeMap<Integer,String>();
        
        int n = Integer.parseInt(args[0]);
        System.out.println("number of keys n = " + n);

        Random rand = new Random(0);
        int[] keys = new int[n];
        for (int i = 0; i < n; i++) { // store n random numbers in [0,2n)
        	keys[i] = rand.nextInt(2*n);
        }

        long startTime = System.currentTimeMillis();
        for (int i = 0; i < n; i++) {
            String item = "item" + i;
            TM.put(keys[i], item);
        }
        long endTime = System.currentTimeMillis();
        long totalTime = endTime - startTime;
        System.out.println("construction time = " + totalTime);
        
        startTime = System.currentTimeMillis();
        for (int i = 0; i < n; i++) {
            if (TM.get(keys[i]) == null) {
                System.out.println("key " + keys[i] + " not found!");
            }
        }
        endTime = System.currentTimeMillis();
        totalTime = endTime - startTime;
        System.out.println("search time = " + totalTime);
    }
}
