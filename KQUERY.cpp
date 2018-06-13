#include <iostream>
#include <vector>
#include <algorithm>
using namespace std;
bool compare0(const vector<int>& v1, const vector<int>& v2 ) {
    return v1[0] < v2[0];
}    
bool compare2(const vector<int>& v1, const vector<int>& v2 ) {
    return v1[2] < v2[2];
}   
int n;
class SegmentTree {
    public:
    SegmentTree(){
        buildTree(0, 0, n-1);
    }
    void update (int ind) {
        updateUTIL(0, 0, n-1, ind);
    }
    int query(int qs, int qe) {
        return queryUTIL(0, 0, n-1, qs, qe);
    }
    private:
    int st[65535];
    void buildTree(int sti, int sts, int ste){
        if (sts == ste) st[sti] = 1;
        else {
            int mid = (sts+ste)/2;
            buildTree(2*sti+1, sts, mid); buildTree(2*sti+2, mid+1, ste);
            st[sti] = st[2*sti+1] + st[2*sti+2];
        }
    }
    int queryUTIL(int sti, int sts, int ste, int qs, int qe) {
        if (qe<sts || ste<qs) return 0;
        else if (qs<=sts && ste<=qe) return st[sti];
        else {
            int mid = (sts+ste)/2;
            return queryUTIL(2*sti+1, sts, mid, qs, qe) + queryUTIL(2*sti+2, mid+1, ste, qs, qe);
        }
    }
    void updateUTIL(int sti, int sts, int ste, int ind) {
        if (ind<sts || ind>ste) return;
        if (sts == ste) st[sti] = 0;
        else {
            int mid = (sts+ste)/2;
            updateUTIL(2*sti+1, sts, mid, ind); updateUTIL(2*sti+2, mid+1, ste, ind);
            st[sti] = st[2*sti+1] + st[2*sti+2];
        }
    }
};

int main()
{
    ios_base::sync_with_stdio(false);
    cin.tie(NULL);
    cin>>n; int arr[n];
    vector<vector<int>> p(n);
    for(int i=0; i<n; i++) {
        cin>>arr[i];
        p[i].push_back(arr[i]);
        p[i].push_back(i);
    }
    int q; cin>>q;
    vector<vector<int>> q_arr(q);
    for(int i=0; i<q; i++) {
        vector<int> row(4);
        cin>>row[0]>>row[1]>>row[2];
        row[0]--; row[1]--; row[3] = i;
        q_arr[i] = row;
    }
    sort(p.begin(), p.end(), compare0);
    sort(q_arr.begin(), q_arr.end(), compare2);
    SegmentTree tree;
    int ans[q];
    int p_ind = 0;
    for (int i=0; i<q; i++) {
        while (p_ind<n && arr[p[p_ind][1]]<=q_arr[i][2]) {
            tree.update(p[p_ind][1]);
            p_ind++;
        }
        ans[q_arr[i][3]] = tree.query(q_arr[i][0], q_arr[i][1]);
    }
    for (int i=0; i<q; i++) {
        cout<<ans[i]<<"\n";
    }
    return 0;
}
