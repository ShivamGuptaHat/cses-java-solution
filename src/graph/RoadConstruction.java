package graph;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;

public class RoadConstruction {

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


    static int[] leader, rank, size;
    static int n, m;
    static int[][] edges;
    public static void solve() throws Exception{
        n = in.nextInt();
        m = in.nextInt();

        edges = new int[m][];
        for (int i = 0; i < m; i++){
            edges[i] = new int[]{in.nextInt(), in.nextInt()};
        }

        leader = new int[n + 1];
        rank = new int[n + 1];
        size = new int[n + 1];

        for (int i = 1; i <= n; i++){
            leader[i] = i;
            rank[i] = 1;
            size[i] = 1;
        }


        int max = 1;
        int comp = n;
        for (int i = 0; i < edges.length; i++){
            boolean response = union(edges[i][0], edges[i][1]);
            if(response){
                comp--;
            }
            max = Math.max(max, Math.max(size[find(edges[i][0])], size[find(edges[i][1])]));
            out.println(comp + " " + max);
        }

    }

    public static int find(int node){
        if(leader[node] != node){
            leader[node] = find(leader[node]);
        }
        return leader[node];
    }

    public static boolean union(int n1, int n2){
        int p1 = find(n1);
        int p2 = find(n2);

        if(p1 == p2){
            return false;
        }

        if(rank[p1] > rank[p2]){
            leader[p2] = p1;
            size[p1] = size[p1] + size[p2];
        }else if(rank[p2] > rank[p1]){
            leader[p1] = p2;
            size[p2] = size[p2] + size[p1];
        }else{
            leader[p2] = p1;
            rank[p1]++;
            size[p1] = size[p1] + size[p2];
        }

        return true;
    }
}