#include <bits/stdc++.h>
using namespace std;

int main() {
    ios::sync_with_stdio(false);
    cin.tie(NULL);

    int x, n;
    cin >> x >> n;

    set<int> lights;
    multiset<int> segments;

    lights.insert(0);
    lights.insert(x);

    segments.insert(x);

    for (int i = 0; i < n; i++) {
        int p;
        cin >> p;

        auto it = lights.upper_bound(p);
        int r = *it;
        int l = *prev(it);

        lights.insert(p);

        segments.erase(segments.find(r - l));

        segments.insert(p - l);
        segments.insert(r - p);

        cout << *segments.rbegin() << " ";
    }
}