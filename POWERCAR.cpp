#include <iostream>
#include <cmath>
#include <climits>
#include <vector>
#include <algorithm>
using namespace std;

typedef long long ll;
// using Long = long long int;

#define endl 				'\n'
#define mid(x, y) 			((x+y)>>1)
#define lc(x) 				((x<<1)|1)
#define rc(x) 				((x+1)<<1)
#define all(p)              p.begin(),p.end()

#define sc1d(a)				scanf("%d", &a)
#define sc2d(a, b)			scanf("%d%d", &a, &b)
#define sc3d(a, b, c)		scanf("%d%d%d", &a, &b, &c)
#define sc4d(a, b, c, d)	scanf("%d%d%d%d", &a, &b, &c, &d)

#define sc1l(a)				scanf("%lld", &a)
#define sc2l(a, b)			scanf("%lld%lld", &a, &b)
#define sc3l(a, b, c)		scanf("%lld%lld%lld", &a, &b, &c)
#define sc4l(a, b, c, d)	scanf("%lld%lld%lld%lld", &a, &b, &c, &d)
void scint (int &result) {
	bool minus = false;
	result = 0;
	char ch;
	ch = getchar();
	while (true) {
		if (ch == '-') break;
		if (ch >= '0' && ch <= '9') break;
		ch = getchar();
	}
	if (ch == '-') minus = true; else result = ch-'0';
	while (true) {
		ch = getchar();
		if (ch < '0' || ch > '9') break;
		result = result*10 + (ch - '0');
	}
	if (minus) result *= -1;
}
int n, K;
void solve() {
	scint(n); scint(K);
	char arr[n];
	ll dp[2][n][K+1];
	scanf("%s", arr);
	for (int i=0; i<=K; i++) {
		dp[0][0][i] = 2;
		dp[1][0][i] = 0;
	}
	for (int i=1; i<n; i++) {
		for (int k=0; k<=K; k++) {
			dp[0][i][k] = dp[1][i][k] = INT_MAX;
		}
	}
	for (int i=1; i<n; i++) {
		if (arr[i] == '#') {
			for (int k=0; k<=K-1; k++) dp[0][i][k] = min(dp[0][i][k], min(dp[0][i-1][k]+2, dp[1][i-1][k+1]+3));
			dp[0][i][K] = min(dp[0][i][K], dp[0][i-1][K]+2);
			for (int k=0; k<=K-1; k++) dp[1][i][k] = min(dp[1][i][k], dp[1][i-1][k+1]+1);
			
		} else {
			for (int k=0; k<=K; k++) dp[0][i][k] = min(dp[0][i][k], min(dp[0][i-1][k]+2, dp[1][i-1][k]+3));
			for (int k=0; k<=K; k++) dp[1][i][k] = min(dp[1][i][k], min(dp[0][i][k]+2, dp[1][i-1][k]+1));
		}
	}
	ll ans = INT_MAX;
	for (int k=0; k<=K; k++) ans = min(ans, dp[1][n-1][k]);
	if (ans == INT_MAX) printf("%s\n", "Impossible");
	else printf("%lld\n", ans);
}
int main() {
    int testCase;
    scanf("%d", &testCase);
    while (testCase--) solve();

	  return 0;
}
