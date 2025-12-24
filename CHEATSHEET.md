### Graph
- **Counting Rooms** — Connected Components (DFS / BFS)
- **Building Roads** — Connected Components
- **Labyrinth** — Shortest Path in Unweighted Grid (BFS)
- **Message Route** — Shortest Path in Undirected Unweighted Graph (BFS)
- **Building Teams** — Bipartite Graph Check
- **Round Trip** — Undirected Graph | Cycle Detection
- **Round Trip II** - Directed Graph | Cycle Detection
- **Monsters** — Multi-source BFS + Escape Path
- **Shortest Routes I** — SSSP | Dijkstra | Non-negative Weights
- **High Score** — SSSP | Directed Graph | Negative Cycle | Bellman–Ford
- **Cycle Finding** — Negative Cycle Path | Bellman–Ford
- **Flight Discount** — SSSP | Double-State Dijkstra
- **Shortest Routes II** — APSP | Floyd–Warshall
- **Flight Routes** - Multi-Dijkstra




> **Bellman–Ford**
> - A negative cycle is a cycle whose total edge weight is negative.
> - If an edge relaxes on the n-th iteration, a negative cycle exists.
> - The relaxed node may lie *after* the cycle (cycle → A → B → x)
> - Follow parent[] n times to ensure landing inside the cycle before reconstruction.
