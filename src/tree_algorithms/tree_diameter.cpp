#include <bits/stdc++.h>
using namespace std;

vector<vector<int>> tree;
vector<int> h;
int maxDiameter = 0;

void dfs(int parent, int node) {
    int maxH = -1, secMaxH = -1;

    for (int child : tree[node]) {
        if (child != parent) {
            dfs(node, child);

            if (h[child] >= maxH) {
                secMaxH = maxH;
                maxH = h[child];
            }
            else if (h[child] > secMaxH) {
                secMaxH = h[child];
            }
        }
    }

    maxDiameter = max(maxDiameter, maxH + secMaxH + 2); // other 1 will be cancelled out

    h[node] = maxH + 1;
}

int main() {
    int n;
    cin >> n;

    tree.resize(n + 1);
    h.resize(n + 1);

    for (int i = 1; i < n; i++) {
        int a, b;
        cin >> a >> b;

        tree[a].push_back(b);
        tree[b].push_back(a);
    }

    dfs(0, 1);

    cout << maxDiameter << "\n";
}