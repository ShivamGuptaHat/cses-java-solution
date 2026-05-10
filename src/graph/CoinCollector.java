package graph;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.*;

public class CoinCollector {

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
    static boolean[] vis;
    static int[] comp;
    static long[] coins;
    static long[] sccCoins;
    static long[] dp;
    static List<Integer>[] dag;
    static java.util.List<Integer> order;

    public static void solve() throws Exception{
        int n = in.nextInt();
        int m = in.nextInt();

        coins = new long[n + 1];

        for (int i = 1; i <= n; i++) {
            coins[i] = in.nextLong();
        }

        adj = new ArrayList[n + 1];
        radj = new ArrayList[n + 1];

        for (int i = 1; i <= n; i++) {
            adj[i] = new ArrayList<>();
            radj[i] = new ArrayList<>();
        }

        int[] from = new int[m];
        int[] to = new int[m];

        for (int i = 0; i < m; i++) {
            int u = in.nextInt();
            int v = in.nextInt();

            adj[u].add(v);
            radj[v].add(u);

            from[i] = u;
            to[i] = v;
        }

        // ---------- Kosaraju Step 1 ----------
        vis = new boolean[n + 1];
        order = new ArrayList<>();

        for (int i = 1; i <= n; i++) {
            if (!vis[i]) {
                dfs1(i);
            }
        }

        // ---------- Kosaraju Step 2 ----------
        comp = new int[n + 1];

        Collections.reverse(order);

        int sccCount = 0;

        for (int node : order) {
            if (comp[node] == 0) {
                sccCount++;
                dfs2(node, sccCount);
            }
        }

        // ---------- SCC Coin Sum ----------
        sccCoins = new long[sccCount + 1];

        for (int i = 1; i <= n; i++) {
            sccCoins[comp[i]] += coins[i];
        }

        // ---------- Build DAG ----------
        dag = new ArrayList[sccCount + 1];

        for (int i = 1; i <= sccCount; i++) {
            dag[i] = new ArrayList<>();
        }

        for (int i = 0; i < m; i++) {
            int cu = comp[from[i]];
            int cv = comp[to[i]];

            if (cu != cv) {
                dag[cu].add(cv);
            }
        }

        // ---------- DP on DAG ----------
        dp = new long[sccCount + 1];

        long ans = 0;

        for (int i = 1; i <= sccCount; i++) {
            ans = Math.max(ans, dfsDP(i));
        }

        out.println(ans);
    }

    static void dfs1(int node) {
        vis[node] = true;

        for (int next : adj[node]) {
            if (!vis[next]) {
                dfs1(next);
            }
        }

        order.add(node);
    }

    static void dfs2(int node, int id) {
        comp[node] = id;

        for (int next : radj[node]) {
            if (comp[next] == 0) {
                dfs2(next, id);
            }
        }
    }

    static long dfsDP(int node) {

        if (dp[node] != 0) {
            return dp[node];
        }

        long best = 0;

        for (int next : dag[node]) {
            best = Math.max(best, dfsDP(next));
        }

        dp[node] = sccCoins[node] + best;

        return dp[node];
    }
}