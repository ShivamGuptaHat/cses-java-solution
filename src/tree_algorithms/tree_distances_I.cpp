#include <bits/stdc++.h>
using namespace std;

const int MAXN = 200001;

vector<int> tree[MAXN];
int distA[MAXN], distB[MAXN];

int bfs(int start, int dist[], int n) {
    queue<int> q;
    fill(dist, dist + n + 1, -1);

    q.push(start);
    dist[start] = 0;

    int far = start;

    while (!q.empty()) {
        int v = q.front();
        q.pop();

        for (int u : tree[v]) {
            if (dist[u] == -1) {
                dist[u] = dist[v] + 1;
                q.push(u);

                if (dist[u] > dist[far])
                    far = u;
            }
        }
    }

    return far;
}

int main() {
    int n;
    cin >> n;

    for (int i = 1; i < n; i++) {
        int a, b;
        cin >> a >> b;

        tree[a].push_back(b);
        tree[b].push_back(a);
    }

    int A = bfs(1, distA, n);
    int B = bfs(A, distA, n);
    bfs(B, distB, n);

    for (int i = 1; i <= n; i++)
        cout << max(distA[i], distB[i]) << " ";
}