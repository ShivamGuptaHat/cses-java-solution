import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

// Find Connected components

public class BuildingRoads {

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

    static int[] parent;
    static int[] rank;

    static int find(int u){
        if(parent[u] != u){
            parent[u] = find(parent[u]); //path compression
        }
        return parent[u];
    }

    static void union(int u, int v){
        int lu = find(u);
        int lv = find(v);
        if(lu == lv) return;

        if(rank[lu] == rank[lv]){
            parent[lv] = lu;
            rank[lu]++;
        }else if(rank[lu] > rank[lv]){
            parent[lv] = lu;
        }else{
            parent[lu] = lv;
        }
    }

    public static void main(String[] args) throws Exception {
        FastScanner fs = new FastScanner(System.in);
        StringBuilder out = new StringBuilder();

        int n = fs.nextInt();
        int m = fs.nextInt();

        parent = new int[n + 1];
        rank = new int[n + 1];

        for (int i = 1; i <= n; i++){
            parent[i] = i;
        }

        List<int[]> edges = new ArrayList<>();

        for (int i = 0; i < m; i++){
            edges.add(new int[]{fs.nextInt(), fs.nextInt()});
        }

        for (int[] edge : edges){
            union(edge[0], edge[1]);
        }

        List<Integer> components = new ArrayList<>();
        for (int i = 1; i <= n; i++){
            if(parent[i] == i){
                components.add(i);
            }
        }

        System.out.println(components.size() - 1);
        for (int i = 1; i < components.size(); i++){
            System.out.println(components.get(0) + " " + components.get(i));
        }

        System.out.flush();
    }
}

