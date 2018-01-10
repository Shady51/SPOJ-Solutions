#include <iostream>

using namespace std;

class SegmentTree {
    public:
        SegmentTree(int arr[], int n) {
            constructTree (arr, 0, n-1, 0);
        }
        long get (int qs, int qe, int n) {
            return getUTIL(0, n-1, 0, qs, qe);
        }
        void update (int qs, int qe, int n) {
            updateUTIL(0, n-1, 0, qs, qe);
        }
    private:
        int st[262144][3] = {{0}};
        int lazy[262144] = {0};
        int getMid(int a, int b) {
            return (a+b)/2;
        }
        void updateUTIL(int sts, int ste, int sti, int qs, int qe) {
            if (lazy[sti] != 0) {
                lazy[sti] %= 3;
                for (int i=0; i<lazy[sti]; i++) {
                    int a = st[sti][0], b = st[sti][1], c = st[sti][2];
                    st[sti][1] = a; st[sti][2] = b; st[sti][0] = c;
                }
                if (sts != ste) {
                    lazy[2*sti+1] += lazy[sti];
                    lazy[2*sti+2] += lazy[sti];
                }
                lazy[sti] = 0;
            }
            
            if (qs>ste || sts>qe) return;
           
            if (sts>=qs && ste<=qe) {
                int a = st[sti][0], b = st[sti][1], c = st[sti][2];
                st[sti][1] = a; st[sti][2] = b; st[sti][0] = c;
                if (sts != ste) {
                    lazy[2*sti+1] += 1;
                    lazy[2*sti+2] += 1;
                }
                return;
            }
            updateUTIL(sts, getMid(sts, ste), 2*sti+1, qs, qe);
            updateUTIL(getMid(sts, ste)+1, ste, 2*sti+2, qs, qe);

            st[sti][0] = st[2*sti+1][0] + st[2*sti+2][0];
            st[sti][1] = st[2*sti+1][1] + st[2*sti+2][1];
            st[sti][2] = st[2*sti+1][2] + st[2*sti+2][2];

        }
        long getUTIL(int sts, int ste, int sti, int qs, int qe) {
            if (lazy[sti] != 0) {
                lazy[sti] %= 3;
                for (int i=0; i<lazy[sti]; i++) {
                    int a = st[sti][0], b = st[sti][1], c = st[sti][2];
                    st[sti][1] = a; st[sti][2] = b; st[sti][0] = c;
                }
                if (sts != ste) {
                    lazy[2*sti+1] += lazy[sti];
                    lazy[2*sti+2] += lazy[sti];
                }
                lazy[sti] = 0;
            }
            if (sts>qe || qs>ste) return 0;
            else if (qs<=sts && qe>=ste) return st[sti][0];
            else return getUTIL(sts, getMid(sts, ste), 2*sti+1, qs, qe) + getUTIL(getMid(sts, ste)+1, ste, 2*sti+2, qs, qe);
        }
        int constructTree (int arr[], int sts, int ste, int sti) {
            if (sts == ste) {
                st[sti][0] = 1;
                return st[sti][0];
            }
            int mid = (sts+ste)/2;
            st[sti][0] = constructTree(arr, sts, mid, 2*sti+1) + constructTree(arr, mid+1, ste, 2*sti+2);
            return st[sti][0];
        }

};

int main() {
    int n, q; scanf("%d %d", &n, &q);
    int arr[n] = {0};
    SegmentTree seg(arr, n);
    while (q-->0) {
        int type, A, B; scanf("%d %d %d", &type, &A, &B);
        if (type == 0) seg.update(A, B, n);
        else printf("%d\n", seg.get(A, B, n));
    }
    return 0;
}
