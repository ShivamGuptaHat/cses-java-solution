package graph;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.*;

public class CycleFinding {

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

    static class Edge{
        int from, to;
        long cost;
        public Edge(int from, int to, long cost){
            this.from = from;
            this.to = to;
            this.cost = cost;
        }
    }

    static FastScanner in = new FastScanner(System.in);
    static PrintWriter out = new PrintWriter(new BufferedOutputStream(System.out));
    static final long INF = (long)1e18;
    public static void main(String[] args) throws Exception {
        /*
            1. Apply Bellman-Ford and detect negative node
            2. Go n step backward to land in cycle
            3. Fetch path
         */
        int n = in.nextInt();
        int m = in.nextInt();
        List<Edge> edgeList = new ArrayList<>();
        for (int i = 0; i < m; i++){
            int from = in.nextInt();
            int to = in.nextInt();
            long cost = in.nextLong();

            edgeList.add(new Edge(from, to, cost));
        }


        int x = -1;

        long[] distance = new long[n + 1];
        int[] parent = new int[n + 1];
        Arrays.fill(distance, INF);
        distance[1] = 0;


        for (int r = 1; r <= n; r++){
            x = -1;
            for (Edge e : edgeList){
                int u = e.from;
                int v = e.to;
                long w = e.cost;
                if(distance[u] + w < distance[v]){
                    distance[v] = distance[u] + w;
                    parent[v] = u;
                    x = v;
                }
            }
        }

        if(x == -1){
            out.println("NO");
            out.flush();
            return;
        }

        // Move backward to land in cycle
        for (int i = 1; i <= n; i++){
            x = parent[x];
        }

        out.println("YES");
        List<Integer> cycle = new ArrayList<>();
        int start = x;
        do {
            cycle.add(x);
            x = parent[x];
        }while(x != start);
        cycle.add(x);

        Collections.reverse(cycle);
        for (int c : cycle){
            out.print(c + " ");
        }
        out.flush();
    }
}
