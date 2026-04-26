package graph;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

public class Monsters {

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


    static int m, n;
    static boolean[][] mIsVisited, aIsVisited;
    static int[][] path;
    static char[][] maze;
    public static void solve() throws Exception{
        int[][] dir = {{-1, 0}, {0, 1}, {1, 0}, {0, -1}};
        String pathDir = "URDL";
        m = in.nextInt();
        n = in.nextInt();
        maze = new char[m][];
        path = new int[m][n];
        for (int i = 0; i < m; i++){
            maze[i] = in.next().toCharArray();
        }

        int[] e = {0 , 0};

        mIsVisited = new boolean[m][n];
        aIsVisited = new boolean[m][n];

        Queue<int[]> queue = new LinkedList<>();
        for (int i = 0; i < m; i++){
            for (int j = 0; j < n; j++){
                if(maze[i][j] == 'M') {
                    queue.add(new int[]{0, i, j});
                    mIsVisited[i][j] = true;
                }
            }
        }

        for (int i = 0; i < m; i++){
            for (int j = 0; j < n; j++){
                if(maze[i][j] == 'A') {
                    queue.add(new int[]{1, i, j});
                    aIsVisited[i][j] = true;
                    e = new int[]{1, i, j};
                }
            }
        }

        int aCount = 1;


        boolean pathFound = true;


        outer:
        while(!queue.isEmpty()){
            e = queue.poll();

            //monster
            if (e[0] == 0) {
                for (int d = 0; d < 4; d++){
                    int nr = e[1] + dir[d][0];
                    int nc = e[2] + dir[d][1];
                    if(isValid(nr, nc) && !mIsVisited[nr][nc]){
                        queue.add(new int[]{0, nr, nc});
                        mIsVisited[nr][nc] = true;
                    }
                }

            //non-monster
            }else{
                if(pathFound(e[1], e[2])){
                    break outer;
                }
                aCount--;
                for (int d = 0; d < 4; d++){
                    int nr = e[1] + dir[d][0];
                    int nc = e[2] + dir[d][1];
                    if(isValid(nr, nc) && !aIsVisited[nr][nc] && !mIsVisited[nr][nc]){
                        queue.add(new int[]{1, nr, nc});
                        aIsVisited[nr][nc] = true;
                        path[nr][nc] = d + 1;
                        aCount++;
                    }
                }

                if(aCount == 0){
                    pathFound = false;
                    break outer;
                }
            }
        }


        if(!pathFound){
            out.println("NO");
        }else{
            out.println("YES");
            StringBuilder ans =  new StringBuilder("");
            int pr = e[1], pc = e[2];
            while(path[pr][pc] != 0){
                int ad = path[pr][pc] - 1;
                ans.append(pathDir.charAt(ad));
                pr = pr - dir[ad][0];
                pc = pc - dir[ad][1];
            }
            out.println(ans.length());
            out.println(ans.reverse());
        }
    }

    public static boolean isValid(int r, int c){
        return r >= 0 && r < m && c >= 0 && c < n && maze[r][c] != '#';
    }

    public static boolean pathFound(int r, int c){
        return r == 0 || r == m - 1 || c == 0 || c == n - 1;
    }


    public static void solve2() throws Exception {

        int[][] dir = {{-1,0},{0,1},{1,0},{0,-1}};
        char[] dc = {'U','R','D','L'};

        m = in.nextInt();
        n = in.nextInt();

        maze = new char[m][n];
        for(int i = 0; i < m; i++) maze[i] = in.next().toCharArray();

        int[][] mon = new int[m][n];
        for(int[] row : mon) Arrays.fill(row, Integer.MAX_VALUE);

        Queue<int[]> mq = new LinkedList<>();
        int sr = 0, sc = 0;

        // init
        for(int i=0;i<m;i++){
            for(int j=0;j<n;j++){
                if(maze[i][j]=='M'){
                    mq.add(new int[]{i,j});
                    mon[i][j]=0;
                }
                if(maze[i][j]=='A'){
                    sr=i; sc=j;
                }
            }
        }

        // monster bfs
        while(!mq.isEmpty()){
            int[] c = mq.poll();
            for(int d=0;d<4;d++){
                int nr=c[0]+dir[d][0], nc=c[1]+dir[d][1];
                if(isValid(nr,nc) && mon[nr][nc]==Integer.MAX_VALUE){
                    mon[nr][nc]=mon[c[0]][c[1]]+1;
                    mq.add(new int[]{nr,nc});
                }
            }
        }

        // player bfs
        Queue<int[]> pq = new LinkedList<>();
        int[][] par = new int[m][n];
        int[][] dist = new int[m][n];
        for(int[] row : dist) Arrays.fill(row, Integer.MAX_VALUE);

        pq.add(new int[]{sr,sc});
        dist[sr][sc]=0;

        if(pathFound(sr,sc)){
            out.println("YES\n0\n");
            return;
        }

        int er=-1, ec=-1;

        while(!pq.isEmpty()){
            int[] c = pq.poll();

            for(int d=0;d<4;d++){
                int nr=c[0]+dir[d][0], nc=c[1]+dir[d][1];

                if(!isValid(nr,nc)) continue;

                int nt = dist[c[0]][c[1]]+1;

                if(nt < mon[nr][nc] && dist[nr][nc]==Integer.MAX_VALUE){
                    dist[nr][nc]=nt;
                    par[nr][nc]=d;
                    pq.add(new int[]{nr,nc});

                    if(pathFound(nr,nc)){
                        er=nr; ec=nc;
                        break;
                    }
                }
            }
            if(er!=-1) break;
        }

        if(er==-1){
            out.println("NO");
            return;
        }

        // build path
        StringBuilder ans = new StringBuilder();
        for(int r=er,c=ec; r!=sr || c!=sc; ){
            int d = par[r][c];
            ans.append(dc[d]);
            r -= dir[d][0];
            c -= dir[d][1];
        }

        out.println("YES");
        out.println(ans.length());
        out.println(ans.reverse());
    }


}