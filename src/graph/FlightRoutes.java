package graph;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.*;

public class FlightRoutes {

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
    public static void main(String[] args) throws Exception {
        solve();
        out.flush();
    }

    static class Edge{
        int to;
        long cost;
        public Edge(int to, long cost){
            this.to = to;
            this.cost = cost;
        }
    }

    public static void solve() throws Exception {
        int n = in.nextInt();
        int m = in.nextInt();
        int k = in.nextInt();

        List<Edge>[] g = new ArrayList[n + 1];
        for (int i = 1; i <= n; i++) g[i] = new ArrayList<>();

        for (int i = 0; i < m; i++) {
            int a = in.nextInt();
            int b = in.nextInt();
            long c = in.nextLong();
            g[a].add(new Edge(b, c));
        }

        PriorityQueue<Edge> pq =
                new PriorityQueue<>(Comparator.comparingLong(e -> e.cost));

        int[] cnt = new int[n + 1];
        pq.add(new Edge(1, 0));

        StringBuilder ans = new StringBuilder();

        while (!pq.isEmpty()) {
            Edge cur = pq.poll();

            if (cnt[cur.to] >= k) continue;
            cnt[cur.to]++;

            if (cur.to == n) {
                ans.append(cur.cost).append(" ");
                if (cnt[n] == k) break;
            }

            for (Edge e : g[cur.to]) {
                pq.add(new Edge(e.to, cur.cost + e.cost));
            }
        }

        out.print(ans);
    }
}
