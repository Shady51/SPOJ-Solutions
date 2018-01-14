#include <iostream>
#include <algorithm>
#include <cmath>

using namespace std;

#define rep(x,y) for(int i=x; i<y; i++)

int block_size;

struct query {
	int qs, qe, ind;
};

bool compare (query x, query y) {
	if (x.qs/block_size != y.qs/block_size) {
		return x.qs/block_size < y.qs/block_size;
	}
	return x.qe < y.qe;
}

int* process (query q_arr[], int arr[], int q) {
	int prev_ans = 0, l = 0, r = 0;
	int* ans = new int[q];
	int freq_arr[1000001] = {0};
	rep(0,1) {
		int qs = q_arr[i].qs, qe = q_arr[i].qe;
		l = qs; r = qe;
		for (int k=qs; k<=qe; k++) {
			freq_arr[arr[k]]++;
			if (freq_arr[arr[k]] == 1) prev_ans++;
		}
		ans[q_arr[i].ind] = prev_ans;
	}
	rep(1,q) {
		int qs = q_arr[i].qs, qe = q_arr[i].qe;
		while (l<qs) {
			freq_arr[arr[l]]--;
			if (freq_arr[arr[l]] == 0) prev_ans--;
			l++;
		}
		while (l>qs) {
			l--;
			freq_arr[arr[l]]++;
			if (freq_arr[arr[l]] == 1) prev_ans++;
		}
		
		while (r<qe) {
			r++;
			freq_arr[arr[r]]++;
			if (freq_arr[arr[r]] == 1) prev_ans++;
		}
		
		while (r>qe) {
			freq_arr[arr[r]]--;
			if (freq_arr[arr[r]] == 0) prev_ans--;
			r--;
		}
		ans[q_arr[i].ind] = prev_ans;
	}
	return ans;
}

int main() {
	int n; scanf("%d", &n); block_size = (int)sqrt(n);
	int arr[n];
	rep(0,n) scanf("%d", &arr[i]);
	int q; scanf("%d", &q);
	query q_arr[q];
	rep(0, q) {
		scanf("%d %d", &q_arr[i].qs, &q_arr[i].qe); q_arr[i].ind = i;
		q_arr[i].qs--; q_arr[i].qe--;
	}
	sort (q_arr, q_arr + q, compare);
	int* ans = process (q_arr, arr, q);
	rep (0,q) {
		printf("%d\n", ans[i]);
	}
	return 0;
}
