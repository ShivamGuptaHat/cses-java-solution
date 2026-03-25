#include <bits/stdc++.h>
using namespace std;

const int MAXN = 200001;

vector<int> tree[MAXN];
long long subDist[MAXN];
long long ans[MAXN];
int subSize[MAXN];
int n;

void dfs1(int node, int parent) {
    subSize[node] = 1;

    for (int child : tree[node]) {
        if (child == parent) continue;

        dfs1(child, node);

        subSize[node] += subSize[child];
        subDist[node] += subDist[child] + subSize[child];
    }
}

void dfs2(int node, int parent) {
    for (int child : tree[node]) {
        if (child == parent) continue;

        ans[child] = ans[node] - subSize[child] + (n - subSize[child]);

        dfs2(child, node);
    }
}

int main() {
    cin >> n;

    for (int i = 1; i < n; i++) {
        int a, b;
        cin >> a >> b;

        tree[a].push_back(b);
        tree[b].push_back(a);
    }

    dfs1(1, 0);

    ans[1] = subDist[1];

    dfs2(1, 0);

    for (int i = 1; i <= n; i++)
        cout << ans[i] << " ";
}