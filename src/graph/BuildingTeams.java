package graph;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

// Bipartite graph - DFS/BFS

public class BuildingTeams {

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

    public static void main(String[] args) throws Exception {
        FastScanner in = new FastScanner(System.in);
        PrintWriter out = new PrintWriter(new BufferedOutputStream(System.out));

        int n = in.nextInt();
        int m = in.nextInt();

        List<Integer>[] g = new ArrayList[n + 1];
        for (int i = 1; i <= n; i++){
            g[i] = new ArrayList<>();
        }

        for (int i = 0; i < m; i++){
            int u = in.nextInt();
            int v = in.nextInt();
            g[u].add(v);
            g[v].add(u);
        }

        boolean[] isVisited = new boolean[n + 1];
        int[] color = new int[n + 1];
        Arrays.fill(color, -1);


        boolean isBipartite = true;
        for (int u = 1; u <= n && isBipartite; u++){
            if(!isVisited[u]){
                isBipartite = dfs(g, u, isVisited, color, 0);
            }
        }

        if(!isBipartite){
            out.println("IMPOSSIBLE");
        }else{
            for (int i = 1; i <= n; i++){
                out.print(color[i] + 1 + " ");
            }
        }

        out.flush();
    }


    public static boolean dfs(List<Integer>[] g, int u, boolean[] isVisited, int[] color, int colorCode){
        isVisited[u] = true;
        color[u] = colorCode;

        for (int v : g[u]){
            if(isVisited[v]){
                if(color[v] == colorCode) return false;
            }else{
                if(!dfs(g, v, isVisited, color, (colorCode + 1) % 2)){
                    return false;
                }
            }
        }

        return true;
    }
}
