//package tree_algorithms;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.sql.Array;
import java.util.ArrayList;
import java.util.List;

public class CompanyQueriesI {

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
        solve2();
        out.flush();
    }

    static int LOG = 20;
    static int[][] up;

    public static void solve2() throws Exception{
        int n = in.nextInt();
        int q = in.nextInt();
        up = new int[n + 1][LOG];

        for (int i = 2; i <= n; i++){
            up[i][0] = in.nextInt();
        }

        for (int j = 1; j < LOG; j++){
            for (int i = 1; i <= n; i++){
                if(up[i][j - 1] != 0){
                    up[i][j] = up[up[i][j - 1]][j - 1];
                }
            }
        }

        while(q-- > 0){
            int x = in.nextInt();
            int l = in.nextInt();

            for (int j = 0; j < LOG; j++){
                if((l & (1 << j)) > 0){
                    x = up[x][j];

                    if(x == 0) break;
                }
            }

            out.println(x == 0 ? "-1" : x);
        }


    }


    static List<Integer>[] tree;
    static int n;

    public static void solve1() throws Exception{
        n = in.nextInt();
        int q = in.nextInt();

        tree = new ArrayList[n + 1];
        for (int i = 1; i <= n; i++){
            tree[i] = new ArrayList<>();
        }

        for (int node = 2; node <= n; node++){
            int leader = in.nextInt();

            tree[leader].add(node);
            tree[node].add(leader);
        }


        for (int qi = 1; qi <= q; qi++){
            int x = in.nextInt();
            int l  =in.nextInt();
            List<Integer> path =  new ArrayList<>();
            dfs(1, -1, path, x);
            if(path.size() >= l){
                out.println(path.get(path.size() - l));
            }else{
                out.println("-1");
            }
        }
    }

    public static boolean dfs(int root, int parent, List<Integer> path, int x){
        if(root == x){
            return true;
        }

        path.add(root);
        for (int c : tree[root]){
            if(c != parent){
                if(dfs(c, root, path, x)){
                    return true;
                }
            }
        }
        path.remove(path.size() - 1);
        return false;
    }


}
