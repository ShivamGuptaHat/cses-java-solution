package graph;

import java.io.*;
import java.util.LinkedList;
import java.util.Queue;


// DFS/BFS

public class CountingRooms {

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

    static int[][] dirs = {{-1, 0}, {1, 0}, {0, - 1}, {0, 1}};
    static int m, n;
    static boolean[][] isVisited;
    static char[][] grid;

    public static void main(String[] args) throws Exception {
        FastScanner in = new FastScanner(System.in);
        PrintWriter out = new PrintWriter(new BufferedOutputStream(System.out));

        m = in.nextInt();
        n = in.nextInt();
        isVisited = new boolean[m][n];

        grid = new char[m][];
        for (int i = 0; i < m; i++){
            grid[i] = in.next().toCharArray();
        }

        int rooms = 0;
        for (int ui = 0; ui < m; ui++){
            for (int uj = 0; uj < n; uj++){
                if(grid[ui][uj] == '.' && !isVisited[ui][uj]){
                    bfs(ui, uj);
                    rooms++;
                }
            }
        }
        out.println(rooms);
        out.flush();
    }


    public static void dfs(int ui, int uj){
        isVisited[ui][uj] = true;
        for (int[] dir : dirs){
            int vi = ui + dir[0];
            int vj = uj + dir[1];

            if(vi >= 0 && vi < m && vj >= 0 && vj < n &&
                    grid[vi][vj] == '.' && !isVisited[vi][vj]){
                dfs(vi, vj);
            }
        }
    }

    public static void bfs(int ui, int uj){
        Queue<int[]> queue = new LinkedList<>();
        queue.add(new int[]{ui, uj});
        isVisited[ui][uj] = true;

        while(!queue.isEmpty()){
            ui = queue.peek()[0];
            uj = queue.remove()[1];

            for (int[] dir : dirs){
                int vi = ui + dir[0];
                int vj = uj + dir[1];

                if(vi >= 0 && vi < m && vj >= 0 && vj < n &&
                        grid[vi][vj] == '.' && !isVisited[vi][vj]){
                    queue.add(new int[]{vi, vj});
                    isVisited[vi][vj] = true;
                }
            }
        }
    }
}
