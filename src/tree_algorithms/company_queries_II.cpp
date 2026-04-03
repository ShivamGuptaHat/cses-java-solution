#include <bits/stdc++.h>
using namespace std;

const int MAXN = 200005;
const int LOG = 20;

vector<int> adj[MAXN];
int up[MAXN][LOG];
int depth[MAXN];

void dfs(int node, int parent) {
    up[node][0] = parent;

    for (int j = 1; j < LOG; j++) {
        if (up[node][j - 1] != 0) {
            up[node][j] = up[ up[node][j - 1] ][j - 1];
        }
    }

    for (int child : adj[node]) {
        if (child != parent) {
            depth[child] = depth[node] + 1;
            dfs(child, node);
        }
    }
}

int lca(int a, int b) {
    if (depth[a] < depth[b]) {
        swap(a, b);
    }

    // Step 1: bring a to same depth
    int diff = depth[a] - depth[b];
    for (int j = 0; j < LOG; j++) {
        if (diff & (1 << j)) {
            a = up[a][j];
        }
    }

    // Step 2: if equal
    if (a == b) return a;

    // Step 3: lift both
    for (int j = LOG - 1; j >= 0; j--) {
        if (up[a][j] != up[b][j]) {
            a = up[a][j];
            b = up[b][j];
        }
    }

    // Step 4: parent
    return up[a][0];
}

int main() {
    int n, q;
    cin >> n >> q;

    for (int i = 1; i <= n; i++) {
        adj[i].clear();
    }

    for (int i = 2; i <= n; i++) {
        int parent;
        cin >> parent;
        adj[parent].push_back(i);
        adj[i].push_back(parent);
    }

    dfs(1, 0);

    while (q--) {
        int a, b;
        cin >> a >> b;
        cout << lca(a, b) << "\n";
    }

    return 0;
}