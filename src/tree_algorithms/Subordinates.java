//package tree_algorithms;

import java.io.*;
import java.util.*;

public class Subordinates {

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
        solveUsingList();
        out.flush();
    }

    public static void solveUsingMap() throws Exception{
        int n = in.nextInt();

        Map<Integer, List<Integer>> tree = new HashMap<>();
        for (int i = 1; i <= n; i++){
            tree.put(i, new ArrayList<>());
        }

        for (int s = 2; s <= n; s++){
            int p = in.nextInt();
            tree.get(p).add(s);
        }

        int[] ans = new int[n + 1];
        dfs(tree, 1, ans);

        StringBuilder sans = new StringBuilder();
        for (int i = 1; i <= n; i++){
            sans.append(ans[i]).append(" ");
        }

        out.print(sans);
    }

    public static int dfs(Map<Integer, List<Integer>> tree, int root, int[] ans){
        int sub = 0;
        for (int c : tree.get(root)){
            sub += dfs(tree, c, ans);
        }

        ans[root] += sub;
        return sub + 1;
    }

    public static void solveUsingList() throws IOException {
        int n = in.nextInt();
        List<Integer>[] tree = new List[n + 1];
        for (int i = 1; i <= n; i++){
            tree[i] = new ArrayList<>();
        }

        for (int s = 2;  s <= n; s++){
            int p = in.nextInt();
            tree[p].add(s);
        }

        int[] ans = new int[n + 1];
        dfs2(tree, 1, ans);

        StringBuilder sans = new StringBuilder();
        for (int i = 1; i <= n; i++){
            sans.append(ans[i]).append(" ");
        }

        out.print(sans);
    }

    public static int dfs2(List<Integer>[] tree, int root, int[] ans){
        int sub = 0;
        for (int s : tree[root]){
            sub += dfs2(tree, s, ans);
        }

        ans[root] = sub;
        return sub + 1;
    }
}
