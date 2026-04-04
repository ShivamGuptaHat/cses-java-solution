package range_queries;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;

public class StaticRangeSumQueries {

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

    static long[] st;
    static long[] nums;
    static int n;


    public static void build(int node, int start, int end){
        if(start == end){
            st[node] = nums[start];
            return;
        }

//        int mid = start + (end - start) / 2;
        int mid = (start + end) / 2;
        build(2 * node, start, mid);
        build(2 * node + 1, mid + 1, end);
        st[node] = st[node * 2] + st[node * 2 + 1];
    }

    public static long query(int l, int r){
        return query(1, 0, n - 1, l, r);
    }

    public static long query(int node, int start, int end, int l, int r){
        if(r < start || l > end) return 0;

        if(l <= start && end <= r) return st[node];

        int mid = (start + end) / 2;
        long left = query(2 * node, start, mid, l, r);
        long right = query(2 * node + 1, mid + 1, end, l , r);
        return left + right;
    }



    public static void solve() throws Exception{
        n = in.nextInt();
        int q = in.nextInt();

        nums = new long[n];
        st = new long[4 * n];

        for (int i = 0; i < n; i++){
            nums[i] = in.nextLong();
        }

        build(1, 0, n - 1);

        while(q-- > 0){
            int l = in.nextInt() - 1;
            int r  =in.nextInt() - 1;

            out.println(query(l, r));
        }
    }
}