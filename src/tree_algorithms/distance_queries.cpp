#include <bits/stdc++.h>
using namespace std;

const int MAXN = 200005;
const int LOG = 20;

vector<int> tree[MAXN];
int up[MAXN][LOG];
int depth[MAXN];

void dfs(int node, int parent) {
    up[node][0] = parent;

    for (int j = 1; j < LOG; j++) {
        if (up[node][j - 1] != 0) {
            up[node][j] = up[up[node][j - 1]][j - 1];
        }
    }

    for (int child : tree[node]) {
        if (child != parent) {
            depth[child] = depth[node] + 1;
            dfs(child, node);
        }
    }
}

int lca(int a, int b) {
    if (depth[a] < depth[b]) swap(a, b);

    int diff = depth[a] - depth[b];
    for (int j = 0; j < LOG; j++) {
        if (diff & (1 << j)) {
            a = up[a][j];
        }
    }

    if (a == b) return a;

    for (int j = LOG - 1; j >= 0; j--) {
        if (up[a][j] != up[b][j]) {
            a = up[a][j];
            b = up[b][j];
        }
    }

    return up[a][0];
}

int main() {
    int n, q;
    cin >> n >> q;

    for (int i = 1; i <= n; i++) {
        tree[i].clear();
        depth[i] = 0;
    }

    for (int i = 2; i <= n; i++) {
        int a, b;
        cin >> a >> b;
        tree[a].push_back(b);
        tree[b].push_back(a);
    }

    dfs(1, 0);

    while (q--) {
        int a, b;
        cin >> a >> b;

        int c = lca(a, b);
        int dist = depth[a] + depth[b] - 2 * depth[c];

        cout << dist << '\n';
    }

    return 0;
}