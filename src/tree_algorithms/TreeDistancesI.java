package tree_algorithms;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.*;

public class TreeDistancesI {

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

        long nextLong() throws IOException {
            int c;
            while ((c = read()) <= ' ') if (c == -1) return -1;
            int sign = 1;
            if (c == '-') { sign = -1; c = read(); }
            long val = c - '0';
            while ((c = read()) > ' ') val = val * 10 + (c - '0');
            return val * sign;
        }

        String next() throws IOException {
            int c;
            while ((c = read()) <= ' ') if (c == -1) return null;
            StringBuilder sb = new StringBuilder();
            sb.append((char)c);
            while ((c = read()) > ' ') sb.append((char)c);
            return sb.toString();
        }
    }

    static long gcd(long a, long b) {
        while (b != 0) { long t = a % b; a = b; b = t; }
        return a;
    }

    static long modPow(long a, long b, long mod) {
        long r = 1;
        while (b > 0) {
            if ((b & 1) == 1) r = (r * a) % mod;
            a = (a * a) % mod;
            b >>= 1;
        }
        return r;
    }

    static FastScanner in = new FastScanner(System.in);
    static PrintWriter out = new PrintWriter(new BufferedOutputStream(System.out));
    static final long INF = (long)1e18;
    static final int MOD = 1_000_000_007;
    public static void main(String[] args) throws Exception {
        solve();
        out.flush();
    }

    static List<Integer>[] tree;

    public static void solve() throws Exception{
        int n = in.nextInt();
        tree = new List[n + 1];

        for (int i = 1; i <= n; i++){
            tree[i] = new ArrayList<>();
        }

        for (int i = 1; i <= n - 1; i++){
            int a = in.nextInt();
            int b = in.nextInt();

            tree[a].add(b);
            tree[b].add(a);
        }

        int[] distanceFromA = new int[n + 1];
        int[] distanceFromB = new int[n + 1];

        int A = bfs(1, distanceFromA);
        int B = bfs(A, distanceFromA);
        bfs(B, distanceFromB);

        for (int i = 1; i <= n; i++){
            out.print(Math.max(distanceFromA[i], distanceFromB[i]) + " ");
        }

    }


    public static int bfs(int start, int[] distance){
        Arrays.fill(distance, -1);

        Queue<Integer> q  = new LinkedList<>();
        q.add(start);
        distance[start] = 0;

        int far = start;

        while(!q.isEmpty()){
            int u = q.remove();
            for (int v : tree[u]){
                if(distance[v] == -1){
                    distance[v] = distance[u] + 1;
                    q.add(v);
                    if(distance[v] > distance[far]){
                        far = v;
                    }
                }
            }
        }

        return far;

    }

    /*

        1. BFS
            - find furthest node and distance vectors
        2. Cal max
     */
}
