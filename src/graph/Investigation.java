package graph;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.*;

public class Investigation {

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
    }

    static FastScanner in = new FastScanner(System.in);
    static PrintWriter out = new PrintWriter(new BufferedOutputStream(System.out));

    static final long INF = (long)1e18;
    static final int MOD = 1_000_000_007;

    public static void main(String[] args) throws Exception {
        solve();
        out.flush();
    }

    static List<Node>[] g;
    static int n, m;

    public static class Node {
        int node;
        long weight;
        public Node(int u, long w) {
            this.node = u;
            this.weight = w;
        }
    }

    // ✅ NEW: Pair for proper Dijkstra
    static class Pair {
        int node;
        long dist;
        Pair(int n, long d) {
            node = n;
            dist = d;
        }
    }

    public static void solve() throws Exception {
        n = in.nextInt();
        m = in.nextInt();

        g = new ArrayList[n + 1];
        for (int u = 1; u <= n; u++) {
            g[u] = new ArrayList<>();
        }

        for (int e = 1; e <= m; e++) {
            int a = in.nextInt();
            int b = in.nextInt();
            long c = in.nextLong();
            g[a].add(new Node(b, c));
        }

        long[] distance = new long[n + 1];
        long[] ways = new long[n + 1];
        int[] minEdges = new int[n + 1];
        int[] maxEdges = new int[n + 1];

        Arrays.fill(distance, INF);
        Arrays.fill(minEdges, Integer.MAX_VALUE);

        PriorityQueue<Pair> pq = new PriorityQueue<>(
                (a, b) -> Long.compare(a.dist, b.dist)
        );

        distance[1] = 0;
        ways[1] = 1;
        minEdges[1] = 0;
        maxEdges[1] = 0;

        pq.offer(new Pair(1, 0));

        while (!pq.isEmpty()) {
            Pair cur = pq.poll();
            int u = cur.node;

            // ✅ skip outdated entries
            if (cur.dist > distance[u]) continue;

            for (Node v : g[u]) {
                long nd = distance[u] + v.weight;

                if (nd < distance[v.node]) {
                    // ✅ update
                    distance[v.node] = nd;
                    ways[v.node] = ways[u];
                    minEdges[v.node] = minEdges[u] + 1;
                    maxEdges[v.node] = maxEdges[u] + 1;

                    pq.offer(new Pair(v.node, nd));

                } else if (nd == distance[v.node]) {
                    // ✅ merge
                    ways[v.node] = (ways[v.node] + ways[u]) % MOD;
                    minEdges[v.node] = Math.min(minEdges[v.node], minEdges[u] + 1);
                    maxEdges[v.node] = Math.max(maxEdges[v.node], maxEdges[u] + 1);

                    // No need to insert 'v' into PQ again because so far adjacent nodes of 'v' are not explored.
                }
            }
        }

        out.print(distance[n] + " " + ways[n] + " " + minEdges[n] + " " + maxEdges[n]);
    }
}