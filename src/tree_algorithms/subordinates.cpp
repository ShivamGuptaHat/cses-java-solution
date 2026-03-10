#include <bits/stdc++.h>
using namespace std;

const int MAXN = 200001;

vector<int> tree[MAXN];
vector<int> ans;

void dfs(int node, int parent) {
    int subordinates = 0;

    for (int child : tree[node]) {
        if(child != parent) {
            dfs(child, node);
            subordinates += 1 + ans[child];
        }
    }

    ans[node] = subordinates;
}

int main() {
    int n;
    cin >> n;

    ans.resize(n + 1);

    for (int i = 2; i <= n; i++) {
        int parent;
        cin >> parent;

        tree[parent].push_back(i);
        tree[i].push_back(parent);
    }

    dfs(1, 0);

    for (int i = 1; i <= n; i++) {
        cout << ans[i] << " ";
    }

    return 0;
}