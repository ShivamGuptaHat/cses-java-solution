package sorting_and_searching;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

public class SubarraySumsII {

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

    public static void solve() throws Exception{
        int n = in.nextInt();
        int target = in.nextInt();

        int[] nums = new int[n];
        for (int i = 0; i < n; i++){
            nums[i] = in.nextInt();
        }

        Map<Long, Long> map = new HashMap<>();
        long sum = 0L;
        long cnt = 0L;
        for (int r = 0; r < n; r++){
            sum += nums[r];
            if(sum == target){
                cnt++;
            }
            if(map.containsKey(sum - target)){
                cnt += map.get(sum - target);
            }
            map.put(sum, map.getOrDefault(sum, 0L) + 1L);
        }

        out.print(cnt);
    }

    public static void solve2() throws Exception {
        int n = in.nextInt();
        long target = in.nextLong();

        Map<Long, Long> map = new HashMap<>();
        map.put(0L, 1L); // important

        long sum = 0L;
        long cnt = 0L;

        for (int i = 0; i < n; i++) {
            sum += in.nextInt();
            cnt += map.getOrDefault(sum - target, 0L);
            map.put(sum, map.getOrDefault(sum, 0L) + 1);
        }

        out.print(cnt);
    }
}
