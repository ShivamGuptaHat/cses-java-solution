package graph;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.*;

// Find cycle in undirected graph

public class RoundTrip {

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


    static List<Integer>[] g;
    static int start = -1, end = -1;
    static boolean[] isVisited;
    static int[] parent;

    public static void main(String[] args) throws Exception {
        FastScanner in = new FastScanner(System.in);
        PrintWriter out = new PrintWriter(new BufferedOutputStream(System.out));

        int n = in.nextInt();
        int m = in.nextInt();
        g = new ArrayList[n + 1];

        for (int i = 1; i <= n; i++){
            g[i] = new ArrayList<>();
        }

        for (int i = 0; i < m; i++){
            int u = in.nextInt();
            int v = in.nextInt();
            g[u].add(v);
            g[v].add(u);
        }
//
//        Map<Integer, Integer> startTime = new HashMap<>();
//        Stack<Integer> path = new Stack<>();
//        boolean found = false;
//        for (int u = 1; u <= n; u++){
//            if(!startTime.containsKey(u)){
//                path = new Stack<>();
//                if(cycleOfSizeMoreThanThree(u, path, startTime, 1)){
//                    found = true;
//                    break;
//                }
//            }
//        }
//
//        if(!found){
//            out.println("IMPOSSIBLE");
//        }else{
//            List<Integer> cycle = new ArrayList<>();
//            int first = path.pop();
//            cycle.add(first);
//            while(path.peek() != first){
//                cycle.add(path.pop());
//            }
//            cycle.add(path.pop());
//            out.println(cycle.size());
//            for (int c : cycle){
//                out.print(c + " ");
//            }
//        }

        isVisited = new boolean[n + 1];
        parent = new int[n + 1];
        Arrays.fill(parent, -1);

        boolean found = false;
        for (int u = 1; u <= n && !found; u++){
            if(!isVisited[u]) {
                found = findCycle(u, -1);
            }
        }


        if(!found)
            out.print("IMPOSSIBLE");
        else {
            List<Integer> cycle = new ArrayList<>();
            cycle.add(start);
            for (int u = end; u != start; u = parent[u]) {
                cycle.add(u);
            }
            cycle.add(start);

            out.println(cycle.size());
            for (int c : cycle)
                out.print(c + " ");
        }

        out.flush();
    }

    // start time approach
    public static boolean cycleOfSizeMoreThanThree(int u,
                                                   Stack<Integer> path,
                                                   Map<Integer, Integer> startTime,
                                                   int time){
        path.push(u);
        startTime.put(u, time);
        for (int v : g[u]){
            if(startTime.containsKey(v)){
                int noOfNodes = time - startTime.get(v) + 1;
                if(noOfNodes >= 3){
                    path.add(v);
                    return true;
                }
            }else{
                if(cycleOfSizeMoreThanThree(v, path, startTime, time + 1)) return true;
            }
        }
        path.pop();
        return false;
    }

    // back edge detection technique
    public static boolean findCycle(int u, int p){
        isVisited[u] = true;
        parent[u] = p;
        for (int v : g[u]){
            if(v == p) continue;

            if(isVisited[v]){
                start = v;
                end = u;
                return true;
            }else{
                if(findCycle(v, u)) return true;
            }
        }
        return false;
    }
}
