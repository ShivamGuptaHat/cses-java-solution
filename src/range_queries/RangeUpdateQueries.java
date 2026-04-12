package range_queries;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Arrays;

public class RangeUpdateQueries {

    private static final class FastScanner {
        private final InputStream in;
        private final byte[] buffer = new byte[1 << 16];
        private int ptr = 0, len = 0;

        FastScanner(InputStream is) {
            this.in = is;
        }

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
            if (c == '-') {
                sign = -1;
                c = read();
            }
            int val = c - '0';
            while ((c = read()) > ' ') val = val * 10 + (c - '0');
            return val * sign;
        }

        long nextLong() throws IOException {
            int c;
            while ((c = read()) <= ' ') if (c == -1) return -1;
            int sign = 1;
            if (c == '-') {
                sign = -1;
                c = read();
            }
            long val = c - '0';
            while ((c = read()) > ' ') val = val * 10 + (c - '0');
            return val * sign;
        }

        String next() throws IOException {
            int c;
            while ((c = read()) <= ' ') if (c == -1) return null;
            StringBuilder sb = new StringBuilder();
            sb.append((char) c);
            while ((c = read()) > ' ') sb.append((char) c);
            return sb.toString();
        }
    }

    static long gcd(long a, long b) {
        while (b != 0) {
            long t = a % b;
            a = b;
            b = t;
        }
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
    static final long INF = (long) 1e18;
    static final int MOD = 1_000_000_007;

    public static void main(String[] args) throws Exception {
        solve();
        out.flush();
    }

    static int n;
    static long[] nums;
    static long[] lazy;

    static void update(int l, int r, long val) {
        update(1, 0, n - 1, l, r, val);
    }

    static void propagate(int node, int start, int end) {
        if (lazy[node] != 0) {
            if (start != end) {
                lazy[node * 2] += lazy[node];
                lazy[node * 2 + 1] += lazy[node];
            }else{
                nums[start] += lazy[node];
            }

            lazy[node] = 0;

        }
    }

    static void update(int node, int start, int end, int l, int r, long val) {
        if (start > r || end < l) return;
        propagate(node, start, end);
        if (start >= l && end <= r) {
            lazy[node] = val;
            propagate(node, start, end);
            return;
        }

        int mid = start + end >> 1;
        update(node * 2, start, mid, l, r, val);
        update(node * 2 + 1, mid + 1, end, l, r, val);
    }

    static long query(int idx) {
        return query(1, 0, n - 1, idx);
    }

    static long query(int node, int start, int end, int idx) {
        propagate(node, start, end);
        if (start == end) {
            return nums[start];
        }

        int mid = start + end >> 1;
        if (idx <= mid) {
            return query(node * 2, start, mid, idx);
        } else {
            return query(node * 2 + 1, mid + 1, end, idx);
        }
    }

    public static void solve() throws Exception {
        n = in.nextInt();
        int q = in.nextInt();

        nums = new long[n];
        for (int i = 0; i < n; i++) {
            nums[i] = in.nextLong();
        }

        lazy = new long[4 * n];
        Arrays.fill(lazy, 0);
        while (q-- > 0) {
            int type = in.nextInt();
            if (type == 1) {
                int a = in.nextInt() - 1;
                int b = in.nextInt() - 1;
                long val = in.nextLong();
                update(a, b, val);
            } else {
                int idx = in.nextInt();
                out.println(query(idx - 1));
            }
        }

    }
}