import java.io.IOException;
import java.io.InputStream;
import java.util.*;

// Shortest path in Grid

public class Labyrinth {

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

    static int[] readIntArray(FastScanner fs, int n) throws Exception {
        int[] arr = new int[n];
        for (int i = 0; i < n; i++) arr[i] = fs.nextInt();
        return arr;
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
        FastScanner fs = new FastScanner(System.in);
        StringBuilder out = new StringBuilder();

        int n = fs.nextInt();
        int m = fs.nextInt();

        char[][] g = new char[n][];
        int ax=-1, ay=-1, bx=-1, by=-1;
        for (int i = 0; i < n; i++){
            g[i] = fs.next().toCharArray();
            for (int j = 0; j < m; j++){
                if(g[i][j] == 'A'){
                    ax = i; ay = j;
                }else if(g[i][j] == 'B'){
                    bx = i; by = j;
                }
            }
        }

        int[][] dirs = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};
        char[] sign = {'U', 'D', 'L', 'R'};

        boolean[][] isVisited = new boolean[n][m];
        int[][] preMove = new int[n][m];

        Queue<int[]> queue = new LinkedList<>();
        queue.add(new int[]{ax, ay});
        isVisited[ax][ay] = true;

        while(!queue.isEmpty()){
            int ux = queue.peek()[0];
            int uy = queue.peek()[1];
            queue.remove();

            for (int d = 0; d < 4; d++){
                int vx = ux + dirs[d][0];
                int vy = uy + dirs[d][1];

                if(vx >= 0 && vx < n && vy >= 0 && vy < m &&
                        !isVisited[vx][vy] && g[vx][vy] != '#'){
                    queue.add(new int[]{vx, vy});
                    isVisited[vx][vy] = true;
                    preMove[vx][vy] = d;
                }
            }
        }

        if(!isVisited[bx][by]){
            System.out.println("NO");
            return;
        }

        System.out.println("YES");
        int steps = 0;
        List<Integer> path = new ArrayList<>();
        int sx = bx, sy = by;
        while(sx != ax || sy != ay){
            steps++;
            int pm = preMove[sx][sy];
            path.add(pm);
            sx = sx - dirs[pm][0];
            sy = sy - dirs[pm][1];
        }

        System.out.println(steps);
        Collections.reverse(path);
        StringBuilder cpath = new StringBuilder();
        for (int p : path){
            cpath.append(sign[p]);
        }

        System.out.println(cpath);
    }


}
