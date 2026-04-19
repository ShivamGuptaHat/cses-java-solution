//package tree_algorithms;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.*;

public class SubtreeQueries {

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

    static long[] value;
    static int[] inTime;
    static int[] outTime;
    static long[] flat;
    static long[] st;
    static List<Integer>[] tree;

    static int n;
    static int timer = 0;



    public static void dfs(int node, int parent){
        timer++;
        inTime[node] = timer;
        flat[timer] = value[node];
        for (int child : tree[node]){
            if(child != parent){
                dfs(child, node);
            }
        }
        outTime[node] = timer;
    }

    public static void build(int node, int start, int end){
        if(start == end){
            st[node] = flat[start];
            return;
        }

        int mid = start + end >> 1;
        build(node * 2, start, mid);
        build(node * 2 + 1, mid + 1, end);
        st[node] = st[node * 2] + st[node * 2 + 1];
    }


    public static void update(int node, int start, int end, int idx, int val){
        if(start == end){
            st[node] = val;
            return;
        }

        int mid = start + end >> 1;
        if(idx <= mid){
            update(node * 2, start, mid, idx, val);
        }else{
            update(node * 2 + 1, mid + 1, end, idx, val);
        }

        st[node] = st[node * 2] + st[node * 2 + 1];
    }

    public static long query(int left, int right){
        return query(1, 1, n, left, right);
    }

    public static long query(int node, int start, int end, int left, int right){
        if(start > right || end < left) return 0;

        if(start >= left && end <= right) return st[node];

        int mid = start + end >> 1;
        long leftResult = query(node * 2, start, mid, left, right);
        long rightResult = query(node * 2 + 1, mid + 1, end, left, right);

        return leftResult + rightResult;

    }

    public static void solve() throws Exception{
        n = in.nextInt();
        int q = in.nextInt();

        value = new long[n + 1];
        for (int node = 1; node <= n; node++){
            value[node] = in.nextInt();
        }

        tree = new ArrayList[n + 1];
        for (int i = 1; i <= n; i++){
            tree[i] = new ArrayList<>();
        }

        for (int e = 1; e <= n - 1; e++){
            int a = in.nextInt();
            int b = in.nextInt();
            tree[a].add(b);
            tree[b].add(a);
        }

        inTime = new int[n + 1];
        outTime = new int[n + 1];
        flat = new long[n + 1];
        st = new long[4 * n];

        dfs(1, 0);
        build(1, 1, n);

        StringBuilder ans = new StringBuilder();

        while(q-- > 0){
            int type = in.nextInt();
            if(type == 1){
                int s = in.nextInt();
                int x = in.nextInt();
                value[s] = x;
                update(1, 1, n, inTime[s], x);
            }else{
                int s = in.nextInt();
                int l = inTime[s];
                int r = outTime[s];
//                out.println(query(l, r));
                ans.append(query(l, r)).append('\n');
            }
        }

        out.print(ans);
    }
}