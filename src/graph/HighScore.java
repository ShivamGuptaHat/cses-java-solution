package graph;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.*;

// Shortest path in a directed graph with cycles and negative edges — Bellman-Ford

public class HighScore {

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

    public static void main(String[] args) throws Exception {
        solve();
        out.flush();
    }

    static class Edge {
        int u, v;
        long w;
        Edge(int u, int v, long w) {
            this.u = u;
            this.v = v;
            this.w = w;
        }
    }

    static void solve() throws Exception{
        int n = in.nextInt();
        int m = in.nextInt();

        List<Edge> edges = new ArrayList<>();
        List<List<Integer>> graph = new ArrayList<>();

        for (int i = 0; i <= n; i++) graph.add(new ArrayList<>());

        for (int i = 0; i < m; i++) {
            int a = in.nextInt();
            int b = in.nextInt();
            long x = in.nextLong();
            edges.add(new Edge(a, b, x));
            graph.get(a).add(b);
        }

        long NEG_INF = (long)(-1e18);
        long[] dist = new long[n + 1];
        Arrays.fill(dist, NEG_INF);
        dist[1] = 0;

        // Bellman-Ford
        for (int i = 1; i <= n - 1; i++) {
            for (Edge e : edges) {
                if (dist[e.u] != NEG_INF && dist[e.u] + e.w > dist[e.v]) {
                    dist[e.v] = dist[e.u] + e.w;
                }
            }
        }

        boolean[] inNegCycle = new boolean[n + 1];

        // Detect negative cycles
        for (Edge e : edges) {
            if (dist[e.u] != NEG_INF && dist[e.u] + e.w > dist[e.v]) {
                inNegCycle[e.v] = true;
            }
        }

        // Reachable from negative cycle nodes
        boolean[] visited = new boolean[n + 1];
        Queue<Integer> q = new LinkedList<>();
        for (int i = 1; i <= n; i++) {
            if (inNegCycle[i]) {
                q.add(i);
                visited[i] = true;
            }
        }

        while (!q.isEmpty()) {
            int u = q.poll();
            for (int v : graph.get(u)) {
                if (!visited[v]) {
                    visited[v] = true;
                    q.add(v);
                }
            }
        }

        if (visited[n]) {
            out.println(-1);
        } else {
            out.println(dist[n]);
        }
    }
}
