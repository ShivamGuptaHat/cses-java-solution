//package graph;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class FlightRoutesCheck {

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


    static List<Integer>[] adj;
    static List<Integer>[] radj;
    static int m, n;

    public static void solve() throws Exception{
        // start
        n = in.nextInt();
        m = in.nextInt();

        adj = new ArrayList[n + 1];
        radj = new ArrayList[n + 1];
        for (int u = 1; u <= n; u++){
            adj[u] = new ArrayList<>();
            radj[u] = new ArrayList<>();
        }


        for (int e = 1; e <= m; e++){
            int a = in.nextInt();
            int b = in.nextInt();

            adj[a].add(b);
            radj[b].add(a);
        }

        List<String> result = new ArrayList<>();
        boolean[] isVisitedAdj = new boolean[n + 1];
        dfs(1, adj, isVisitedAdj);

        for (int u = 2; u <= n; u++){
            if(!isVisitedAdj[u]){
                result.add("1 " + u);
            }
        }


        boolean[] isVisitedRadj = new boolean[n + 1];
        dfs(1, radj, isVisitedRadj);

        for (int u = 2; u <= n; u++){
            if(!isVisitedRadj[u]){
                result.add(u + " 1");
            }
        }


        if(result.size() == 0){
            out.println("YES");
        }else {
            out.println("NO");
            out.println(result.get(0));
        }
    }

    public static void dfs(int node, List<Integer>[] g, boolean[] isVisited){
        isVisited[node] = true;
        for (int child : g[node]){
            if(!isVisited[child]){
                dfs(child, g, isVisited);
            }
        }
    }
}