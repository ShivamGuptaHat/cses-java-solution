package sorting_and_searching;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.*;

public class ConcertTickets {

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
        solveUsingUnionFind();
    }

    public static void solveUsingTreeMap() throws Exception{
        int n = in.nextInt();
        int m = in.nextInt();

        TreeMap<Integer, Integer> map = new TreeMap<>();
        for (int i = 0; i < n; i++){
            int t = in.nextInt();
            map.put(t, map.getOrDefault(t, 0) + 1);
        }

        int[] ct = new int[m];
        for (int i = 0; i < m; i++){
            ct[i]  = in.nextInt();
        }

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < m; i++){
            Integer k = map.floorKey(ct[i]);
            if(k == null){
                sb.append("-1\n");
            }else{
                int v = map.get(k);
                if(v == 1){
                    map.remove(k);
                }else{
                    map.put(k, v - 1);
                }
                sb.append(k).append('\n');
            }
        }
        out.print(sb);
        out.flush();
    }

    public static void solveUsingTreeSet() throws Exception{
        int n = in.nextInt();
        int m = in.nextInt();

        TreeSet<Integer> set = new TreeSet<>();
        Map<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < n ;i++){
            int t = in.nextInt();
            set.add(t);
            map.put(t, map.getOrDefault(t, 0) + 1);
        }

        int[] ct = new int[m];
        for (int i = 0; i < m; i++){
            ct[i]  = in.nextInt();
        }


        StringBuilder sb =  new StringBuilder();
        for (int i = 0; i < m; i++){
            Integer v = set.floor(ct[i]);
            if(v != null){
                sb.append(v).append('\n');
                int f = map.get(v);
                if (f == 1){
                    map.remove(v);
                    set.remove(v);
                }else{
                    map.put(v, f - 1);
                }
            }else{
                sb.append("-1\n");
            }
        }

        out.print(sb);
        out.flush();
    }


    static int[] parent;

    static int find(int x) {
        if (x < 0) return -1;
        if (parent[x] == x) return x;
        return parent[x] = find(parent[x]);
    }

    static int upperBound(int[] a, int x) {
        int l = 0, r = a.length; // [l, r)
        while (l < r) {
            int mid = (l + r) >>> 1;
            if (a[mid] <= x) l = mid + 1;
            else r = mid;
        }
        return l - 1;
    }

    static int lowerBound(int[] a, int x) {
        int l = 0, r = a.length; // [l, r)
        while (l < r) {
            int mid = (l + r) >>> 1;
            if (a[mid] < x) l = mid + 1;
            else r = mid;
        }
        return l;
    }


    public static void solveUsingUnionFind() throws Exception{
        int n = in.nextInt();
        int m = in.nextInt();

        int[] tickets = new int[n];
        for (int i = 0; i < n; i++) tickets[i] = in.nextInt();
        Arrays.sort(tickets);

        parent = new int[n];
        for (int i = 0; i < n; i++) parent[i] = i;

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < m; i++) {
            int x = in.nextInt();

            int idx = upperBound(tickets, x);
            idx = find(idx);

            if (idx == -1) {
                sb.append("-1\n");
            } else {
                sb.append(tickets[idx]).append('\n');
                parent[idx] = idx - 1;
            }
        }

        out.print(sb);
        out.flush();
    }
}
