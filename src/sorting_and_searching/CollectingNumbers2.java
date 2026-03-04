package sorting_and_searching;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashSet;

public class CollectingNumbers2 {

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

    public static void solve() throws Exception {
        int n = in.nextInt();
        int m = in.nextInt();

        int[] arr = new int[n + 1];
        int[] pos = new int[n + 1];

        for (int i = 1; i <= n; i++) {
            arr[i] = in.nextInt();
            pos[arr[i]] = i;
        }

        int rounds = 1;
        for (int i = 1; i < n; i++) {
            if (pos[i] > pos[i + 1]) rounds++;
        }

        while (m-- > 0) {
            int i = in.nextInt();
            int j = in.nextInt();

            int a = arr[i];
            int b = arr[j];

            // Collect affected values
            ArrayList<Integer> affected = new ArrayList<>();
            affected.add(a);
            affected.add(b);

            for (int x : new int[]{a - 1, a + 1, b - 1, b + 1}) {
                if (x >= 1 && x <= n) affected.add(x);
            }

            // Remove old contributions
            for (int x : affected) {
                if (x >= 1 && x < n) {
                    if (pos[x] > pos[x + 1]) rounds--;
                }
            }

            // Swap
            arr[i] = b;
            arr[j] = a;
            pos[a] = j;
            pos[b] = i;

            // Add new contributions
            for (int x : affected) {
                if (x >= 1 && x < n) {
                    if (pos[x] > pos[x + 1]) rounds++;
                }
            }

            out.println(rounds);
        }
    }
}
