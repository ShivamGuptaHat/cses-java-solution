//package sorting_and_searching;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;
import java.util.TreeSet;

public class TrafficLights {

    private static final class FastScanner {
        private final InputStream in;
        private final byte[] buffer = new byte[1 << 16];
        private int ptr = 0, len = 0;

        FastScanner(InputStream is) { this.in = is; }

        private int read() throws IOException {
            if (ptr >= len) {
                len = in.read(buffer);
                ptr = 0;
                if (len <= 0) return -1;
            }
            return buffer[ptr++];
        }

        int nextInt() throws IOException {
            int c;
            while ((c = read()) <= ' ') if (c == -1) return -1;
            int sign = 1;
            if (c == '-') { sign = -1; c = read(); }
            int val = c - '0';
            while ((c = read()) > ' ') val = val * 10 + (c - '0');
            return val * sign;
        }
    }

    static FastScanner in = new FastScanner(System.in);
    static PrintWriter out = new PrintWriter(new BufferedOutputStream(System.out));
    public static void main(String[] args) throws Exception {
        solve();
        out.flush();
    }

    public static void solve() throws Exception{
        int x = in.nextInt();
        int n = in.nextInt();

        int[] A = new int[n];
        for (int i = 0; i < n; i++){
            A[i] = in.nextInt();
        }

        TreeSet<Integer> set = new TreeSet<>();
        TreeMap<Integer, Integer> map = new TreeMap<>();

        set.add(0);
        set.add(x);

        map.put(x, 1);

        for (int i = 0; i < n; i++){
            int floor = set.floor(A[i]);
            int ceil = set.ceiling(A[i]);

            int prevSegment = ceil - floor;
            int leftSegment = A[i] - floor;
            int rightSegment = ceil - A[i];

            set.add(A[i]);
            map.put(prevSegment, map.get(prevSegment) - 1);
            if(map.get(prevSegment) == 0){
                map.remove(prevSegment);
            }

            map.put(leftSegment, map.getOrDefault(leftSegment, 0) + 1);
            map.put(rightSegment, map.getOrDefault(rightSegment, 0) + 1);

            out.print(map.lastKey() + " ");
        }

    }
}
