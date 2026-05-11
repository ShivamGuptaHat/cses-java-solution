package graph;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.*;

public class TeleportersPath {

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


    static int n, m;
    static List<Integer>[] graph;
    static int[] index;
    static int[] inDegree, outDegree;
    static List<Integer> path;

    public static void solve() throws Exception{
        n = in.nextInt();
        m = in.nextInt();

        graph = new ArrayList[n + 1];
        for (int i = 1; i <= n; i++){
            graph[i] = new ArrayList<>();
        }

        inDegree = new int[n + 1];
        outDegree = new int[n + 1];



        for (int i = 0; i < m; i++){
            int from = in.nextInt();
            int to = in.nextInt();

            graph[from].add(to);
            outDegree[from]++;
            inDegree[to]++;
        }


        boolean ok = true;
        for (int i = 1; i <= n && ok; i++){
            if(i == 1){
                if(outDegree[i] != inDegree[i] + 1){
                    ok = false;
                }
            } else if(i == n){
                if(inDegree[i] != outDegree[i] + 1){
                    ok = false;
                }
            } else{
                if(inDegree[i] != outDegree[i]){
                    ok = false;
                }
            }
        }

        if(!ok){
            out.println("IMPOSSIBLE");
            return;
        }

        index = new int[n + 1];
        path = new ArrayList<>();
        dfs2(1);
        Collections.reverse(path);
        if(path.size() != m + 1){
            out.println("IMPOSSIBLE");
        }else{
            StringBuilder res = new StringBuilder();
            for (int p : path){
                res.append(p).append(" ");
            }

            out.println(res);
        }
    }

    public static void dfs(int u){
        while(index[u] < graph[u].size()){
            int v = graph[u].get(index[u]);
            index[u]++;
            dfs(v);
        }
        path.add(u);
    }

    public static void dfs2(int start){

        Stack<Integer> stack = new Stack<>();
        stack.push(start);

        while(!stack.isEmpty()){

            int u = stack.peek();

            if(index[u] < graph[u].size()){

                int v = graph[u].get(index[u]);
                index[u]++;

                stack.push(v);

            } else{

                path.add(u);
                stack.pop();
            }
        }
    }
}