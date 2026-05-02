package graph;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.sql.Array;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class GameRoutes {

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

    public static void solve() throws Exception {
        int n = in.nextInt();
        int m = in.nextInt();

        List<Integer>[] g = new ArrayList[n + 1];
        for (int i = 1; i <= n; i++){
            g[i] = new ArrayList<>();
        }

        int[] indegree = new int[n + 1];

        for (int i = 0; i < m; i++){
            int a = in.nextInt();
            int b = in.nextInt();
            indegree[b]++;
            g[a].add(b);
        }

        Queue<Integer> queue = new LinkedList<>();

        for (int i = 1; i <= n; i++){
            if(indegree[i] == 0){
                queue.add(i);
            }
        }

        long[] dp = new long[n + 1];
        dp[1] = 1l;


        while(!queue.isEmpty()){
            int u = queue.poll();

            for (int v : g[u]){
                dp[v] = (dp[v] + dp[u]) % MOD;
                indegree[v]--;
                if(indegree[v] == 0){
                    queue.add(v);
                }
            }
        }

        out.print(dp[n]);
    }
}