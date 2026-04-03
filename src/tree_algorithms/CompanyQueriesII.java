//package tree_algorithms;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class CompanyQueriesII {

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

    static int[][] up;
    static int LOG = 20;
    static List<Integer>[] tree;
    static int[] depth;

    public static void solve() throws Exception {
        int n = in.nextInt();
        int q = in.nextInt();

        tree = new ArrayList[n + 1];
        depth = new int[n + 1];
        up = new int[n + 1][LOG];

        for (int i = 1; i <= n; i++){
            tree[i] = new ArrayList<>();
        }

        for (int a = 2; a <= n; a++){
            int b = in.nextInt();
            tree[a].add(b);
            tree[b].add(a);
        }

        dfs(1, 0);

        StringBuilder res = new StringBuilder();
        while(q-- > 0){
            int a = in.nextInt();
            int b = in.nextInt();

            res.append(lca(a, b)).append('\n');
        }

        out.println(res);

    }

    public static void dfs(int node, int parent){
        up[node][0] = parent;

        for (int j = 1; j < LOG; j++){
            if (up[node][j - 1] != 0) {
                up[node][j] = up[up[node][j - 1]][j - 1];
            }
        }

        for (int child : tree[node]){
            if(child != parent){
                depth[child] = depth[node] + 1;
                dfs(child, node);
            }
        }
    }

    public static int lca(int a, int b){
        if(depth[a] < depth[b]){
            int temp = a;
            a = b;
            b = temp;
        }


        int diff = depth[a] - depth[b];
        for (int j = 0; j < LOG; j++){
            if((diff & (1 << j)) > 0){
                a = up[a][j];
            }
        }

        if(a == b) return a;

        for (int j = LOG - 1; j >= 0; j--){
            if(up[a][j] != up[b][j]){
                a = up[a][j];
                b = up[b][j];
            }
        }

        return up[a][0];
    }
}
