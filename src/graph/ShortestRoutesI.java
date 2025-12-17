package graph;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.*;

// SSSP - Dijkstra's Algo

public class ShortestRoutesI {

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

    static class Pair{
        int node;
        long distance;
        public Pair(int node, long distance){
            this.node = node;
            this.distance = distance;
        }
    }

    public static void main(String[] args) throws Exception {
        FastScanner in = new FastScanner(System.in);
        PrintWriter out = new PrintWriter(new BufferedOutputStream(System.out));

        int n = in.nextInt();
        int m = in.nextInt();


        List<Pair>[] g = new ArrayList[n + 1];
        for (int i = 1; i <= n; i++){
            g[i] = new ArrayList<>();
        }

        for(int i = 0; i < m; i++){
            int a = in.nextInt();
            int b = in.nextInt();
            int c = in.nextInt();
            g[a].add(new Pair(b, c));
        }

        // Dijkstra's Algo
        PriorityQueue<Pair> pq = new PriorityQueue<>(Comparator.comparingLong(p -> p.distance));
        long[] distance = new long[n + 1];

        Arrays.fill(distance, Long.MAX_VALUE);
        pq.offer(new Pair(1, 0));
        distance[1] = 0;

        while(!pq.isEmpty()){
            Pair u = pq.poll();
            if(u.distance != distance[u.node])
                continue;

            for (Pair v : g[u.node]){
                long dist = u.distance + v.distance;
                if(dist < distance[v.node]){
                    distance[v.node] = dist;
                    pq.offer(new Pair(v.node, dist));
                }
            }
        }

        for (int i = 1; i <= n; i++){
            out.print(distance[i] + " ");
        }

        out.flush();
    }
}
