// Auxiliary methods for reading input data
// Source :  R. Sedgewick, Algorithms in Java

import java.io.*;

// DO NOT MODIFY!

public class In {

    private static int c;

    private static boolean blank() {
        return Character.isWhitespace((char) c);
    }

    private static void readC() {
        try {
            c = System.in.read();
        } catch (IOException e) {
            c = -1;
        }
    }

    // initialize input stream
    public static void init() {
        readC();
    }

     // test if input stream is empty
    public static boolean empty() {
        return c == -1;
    }

    // read string from input stream
    public static String getString() {
        if (empty()) {
            return null;
        }
        String s = "";
        while (!(empty() || blank())) {
            s += (char) c;
            readC();
        }
        while (!empty() && blank()) {
            readC();
        }
        return s;
    }

    // read int from input stream
    public static int getInt() {
        return Integer.parseInt(getString());
    }

    // read double from input stream
    public static double getDouble() {
        return Double.parseDouble(getString());
    }
}
