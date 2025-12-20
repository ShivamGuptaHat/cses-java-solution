package graph;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.*;

public class FlightDiscount {

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

    static class Edge {
        int to;
        long cost;
        Edge(int to, long cost) {
            this.to = to;
            this.cost = cost;
        }
    }

    static class State {
        int node;
        int used;   // 0 = coupon not used, 1 = used
        long dist;
        State(int node, int used, long dist) {
            this.node = node;
            this.used = used;
            this.dist = dist;
        }
    }

    public static void main(String[] args) throws Exception {
        int n = in.nextInt();
        int m = in.nextInt();

        List<Edge>[] graph = new ArrayList[n + 1];
        for (int i = 1; i <= n; i++) graph[i] = new ArrayList<>();

        for (int i = 0; i < m; i++) {
            int a = in.nextInt();
            int b = in.nextInt();
            long c = in.nextLong();
            graph[a].add(new Edge(b, c));
        }

        long[][] dist = new long[n + 1][2];
        for (int i = 1; i <= n; i++) {
            dist[i][0] = INF;
            dist[i][1] = INF;
        }

        PriorityQueue<State> pq =
                new PriorityQueue<>(Comparator.comparingLong(s -> s.dist));

        dist[1][0] = 0;
        pq.add(new State(1, 0, 0));

        while (!pq.isEmpty()) {
            State cur = pq.poll();
            int u = cur.node;
            int used = cur.used;
            long d = cur.dist;

            if (d != dist[u][used]) continue;

            for (Edge e : graph[u]) {
                int v = e.to;
                long w = e.cost;

                if (d + w < dist[v][used]) {
                    dist[v][used] = d + w;
                    pq.add(new State(v, used, dist[v][used]));
                }

                if (used == 0) {
                    long discounted = d + w / 2;
                    if (discounted < dist[v][1]) {
                        dist[v][1] = discounted;
                        pq.add(new State(v, 1, discounted));
                    }
                }
            }
        }

        out.println(dist[n][1]);
        out.flush();
    }
}
