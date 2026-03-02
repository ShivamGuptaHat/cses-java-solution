package sorting_and_searching;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.ArrayDeque;
import java.util.Deque;

public class MaximumSubarraySumII {

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


    // 6 2 6
    // -1 -1 2 -5 2 1
    public static void solve() throws Exception {
        int n = in.nextInt();
        int a = in.nextInt();
        int b = in.nextInt();

        long[] arr = new long[n];
        for (int i = 0; i < n; i++) {
            arr[i] = in.nextLong();
        }

        long[] prefix = new long[n + 1];
        for (int i = 1; i <= n; i++) {
            prefix[i] = prefix[i - 1] + arr[i - 1];
        }

        Deque<Integer> dq = new ArrayDeque<>();
        long ans = Long.MIN_VALUE;

        /*
            -> Looping on Prefix
         */
        for (int r = a; r <= n; r++) {
            int addIndex = r - a;
            while (!dq.isEmpty() && prefix[dq.peekLast()] >= prefix[addIndex]) {
                dq.pollLast();
            }
            dq.addLast(addIndex);

            while (!dq.isEmpty() && dq.peekFirst() < r - b) {
                dq.pollFirst();
            }

            ans = Math.max(ans, prefix[r] - prefix[dq.peekFirst()]);
        }


        for (int r = a; r <= n; r++){
            int addIdx = r - a;
            while(!dq.isEmpty() && prefix[dq.peekLast()] >= prefix[addIdx]){
                dq.pollLast();
            }
            dq.addLast(addIdx);

            while(!dq.isEmpty() && dq.peekFirst())
        }

        out.println(ans);
    }
}
