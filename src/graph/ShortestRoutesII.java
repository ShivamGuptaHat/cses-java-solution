package graph;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Arrays;

// All pair shortest path - Floyd-Warshall algorithm

public class ShortestRoutesII {

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

    public static void main(String[] args) throws Exception {
        FastScanner in = new FastScanner(System.in);
        PrintWriter out = new PrintWriter(new BufferedOutputStream(System.out));

        final long INF = (long)1e18;
        int n = in.nextInt();
        int m = in.nextInt();
        int q = in.nextInt();

        long[][] distance = new long[n + 1][n + 1];
        for (int i = 1; i <= n; i++){
            Arrays.fill(distance[i], INF);
            distance[i][i] = 0;
        }

        for (int e = 0; e < m; e++){
            int from = in.nextInt();
            int to = in.nextInt();
            int w = in.nextInt();

            distance[from][to] = Math.min(distance[from][to], w);
            distance[to][from] = distance[from][to];
        }

        for (int k = 1; k <= n; k++){
            for (int u = 1; u <= n; u++){
                for (int v = 1; v <= n; v++){
                    distance[u][v] = Math.min(distance[u][v], distance[u][k] + distance[k][v]);
                }
            }
        }

        for (int i = 0; i < q; i++){
            int from = in.nextInt();
            int to = in.nextInt();
            out.println(distance[from][to] != INF ? distance[from][to] : -1);
        }
        out.flush();
    }
}
