package tree_algorithms;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.*;

public class TreeDistancesII {

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

    static List<Integer>[] tree;
    static long[] subSize;
    static long[] subDist;
    static long[] ans;
    static int n;

    public static void solve() throws Exception{
        n = in.nextInt();
        tree = new List[n + 1];

        for (int i = 1; i <= n; i++){
            tree[i] = new ArrayList<>();
        }

        for (int i = 1; i <= n - 1; i++){
            int a = in.nextInt();
            int b = in.nextInt();

            tree[a].add(b);
            tree[b].add(a);
        }

        subDist = new long[n + 1];
        subSize = new long[n + 1];
        ans = new long[n + 1];

        dfs1(1, 0);
        ans[1] = subDist[1];
        dfs2(1, 0);

        StringBuilder res = new StringBuilder();
        for (int u = 1; u <= n; u++){
            res.append(ans[u]).append(" ");
        }

        out.print(res);
    }


    public static void dfs1(int node, int parent){
        subSize[node] = 1;
        for (int child : tree[node]){
            if (child != parent){
                dfs1(child, node);
                subSize[node] += subSize[child];
                subDist[node] += subDist[child] + subSize[child];
            }
        }
    }


    public static void dfs2(int node, int parent){
        for (int child : tree[node]){
            if(child != parent){
                ans[child] = ans[node] - subSize[child] + (n - subSize[child]);
                dfs2(child, node);
            }
        }
    }
}
