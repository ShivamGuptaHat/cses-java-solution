#include <bits/stdc++.h>
using namespace std;

vector<vector<int>> tree;
vector<vector<int>> dp;

void dfs(int parent, int node) {
    int total = 0;

    for (int child : tree[node]) {
        if (child != parent) {
            dfs(node, child);
            total += dp[1][child];
        }
    }

    dp[0][node] = total;
    dp[1][node] = total;

    for (int child : tree[node]) {
        if (child != parent) {
            dp[1][node] = max(
                dp[1][node],
                total - dp[1][child] + dp[0][child] + 1
            );
        }
    }
}

void solve() {
    int n;
    cin >> n;

    tree.assign(n + 1, vector<int>());
    dp.assign(2, vector<int>(n + 1));

    for (int i = 1; i < n; i++) {
        int a, b;
        cin >> a >> b;

        tree[a].push_back(b);
        tree[b].push_back(a);
    }

    dfs(0, 1);

    cout << max(dp[0][1], dp[1][1]);
}

int main() {
    solve();
}