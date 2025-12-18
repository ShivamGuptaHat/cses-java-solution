# Algorithms Used (CSES – Java)

### DFS 
```java
void dfs(int u) {
    visited[u] = true;
    for (int v : graph[u]) {
        if (!visited[v]) {
            dfs(v);
        }
    }
}
```

### BFS 
```java
Queue<Integer> q = new LinkedList<>();
visited[src] = true;
q.add(src);

while (!q.isEmpty()) {
    int u = q.poll();
    for (int v : graph[u]) {
        if (!visited[v]) {
            visited[v] = true;
            q.add(v);
        }
    }
}
```

### Union-Find 
```java
int find(int x) {
    if (parent[x] != x) {
        parent[x] = find(parent[x]);
    }
    return parent[x];
}

void union(int a, int b) {
    a = find(a);
    b = find(b);
    if (a == b) return;

    if (rank[a] < rank[b]) {
        parent[a] = b;
    } else if (rank[a] > rank[b]) {
        parent[b] = a;
    } else {
        parent[b] = a;
        rank[a]++;
    }
}
```

### Dijkstra (SSSP – Non-negative Weights)
```java
PriorityQueue<long[]> pq =
        new PriorityQueue<>(Comparator.comparingLong(a -> a[1]));

dist[src] = 0;
pq.add(new long[]{src, 0});

while (!pq.isEmpty()) {
    long[] cur = pq.poll();
    int u = (int) cur[0];
    long d = cur[1];
    
    if (d != dist[u]) continue;

    for (long[] e : graph[u]) {
        int v = (int) e[0];
        long w = e[1];
        if (dist[u] + w < dist[v]) {
            dist[v] = dist[u] + w;
            pq.add(new long[]{v, dist[v]});
        }
    }
}
```

### Floyd-Warshall (All-Pairs Shortest Path)
```java
for (int k = 1; k <= n; k++) {
    for (int i = 1; i <= n; i++) {
        for (int j = 1; j <= n; j++) {
            if (dist[i][k] + dist[k][j] < dist[i][j]) {
                dist[i][j] = dist[i][k] + dist[k][j];
            }
        }
    }
}
```

### Bellman-Ford
```java
for (int i = 1; i <= n - 1; i++) {
    for (Edge e : edges) {
        if (dist[e.u] != INF && dist[e.u] + e.w < dist[e.v]) {
            dist[e.v] = dist[e.u] + e.w;
        }
    }
}

// Detect negative cycles
for (Edge e : edges) {
    if (dist[e.u] != NEG_INF && dist[e.u] + e.w < dist[e.v]) {
        inNegCycle[e.v] = true;
    }
}
```










