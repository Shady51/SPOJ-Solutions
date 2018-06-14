import java.io.*;
import java.util.StringTokenizer;
import static java.lang.Math.*;

class SegmentTree {
    private long st[][];
    protected SegmentTree(int n) {
        int x = (int)(ceil(log(n)/log(2)));
        st = new long[(1<<(x+1)) - 1][2];
    }
    private void updateUTIL (int sti, int sts, int ste, int qs, int qe, long val) {
        if (st[sti][1] != 0) {
            st[sti][0] += (ste-sts+1)*st[sti][1];
            if (sts != ste) {
                st[(sti<<1)|1][1] += st[sti][1];
                st[(sti+1)<<1][1] += st[sti][1];
            }
            st[sti][1] = 0;
        }
        if (ste<qs || qe<sts) return;
        else if (sts>=qs && ste<=qe) {
            st[sti][0] += (ste-sts+1)*val;
            if (sts != ste) {
                st[(sti<<1)|1][1] += val;
                st[(sti+1)<<1][1] += val;
            }
        }
        else {
            int mid = (sts + ste) / 2;
            updateUTIL((sti << 1) | 1, sts, mid, qs, qe, val);
            updateUTIL((sti + 1) << 1, mid + 1, ste, qs, qe, val);
            st[sti][0] = st[(sti << 1) | 1][0] + st[(sti + 1) << 1][0];
        }
    }
    protected void update (int n, int qs, int qe, long val) {
        updateUTIL(0,0, n-1, qs, qe, val);
    }
    private long querySumUTIL (int sti, int sts, int ste, int qs, int qe) {
        if (st[sti][1] != 0) {
            st[sti][0] += (ste-sts+1)*st[sti][1];
            if (sts != ste) {
                st[(sti<<1)|1][1] += st[sti][1];
                st[(sti+1)<<1][1] += st[sti][1];
            }
            st[sti][1] = 0;
        }
        if (ste<qs || qe<sts) return 0;
        else if (qs<=sts && ste<=qe) return st[sti][0];
        else {
            int mid = (sts + ste) / 2;
            return querySumUTIL((sti << 1) | 1, sts, mid, qs, qe) + querySumUTIL((sti + 1) << 1, mid + 1, ste, qs, qe);
        }
    }
    protected long querySum (int n, int qs, int qe) {
        return querySumUTIL(0, 0, n-1, qs, qe);
    }
}
class Main {
    static class Reader {
        private BufferedReader br;
        private StringTokenizer token;
        protected Reader(FileReader obj) {
            br = new BufferedReader(obj, 32768);
            token = null;
        }
        protected Reader() {
            br = new BufferedReader(new InputStreamReader(System.in), 32768);
            token = null;
        }
        protected String next() {
            while(token == null || !token.hasMoreTokens()) {
                try {
                    token = new StringTokenizer(br.readLine());
                } catch (Exception e) {e.printStackTrace();}
            } return token.nextToken();
        }
        protected int nextInt() {return Integer.parseInt(next());}
        protected long nextLong() {return Long.parseLong(next());}
        protected double nextDouble() {return Double.parseDouble(next());}
    }
    public static void main(String[] args) throws IOException {
        Reader in = new Reader();
        PrintWriter out = new PrintWriter(System.out);
        int t = in.nextInt();
        while (t-->0) {
            int n = in.nextInt(), c = in.nextInt();
            SegmentTree tree = new SegmentTree(n);
            while (c-->0){
                int type = in.nextInt();
                if (type == 0) {
                    int qs = in.nextInt()-1, qe = in.nextInt()-1;
                    long val = in.nextInt();
                    tree.update(n, qs, qe, val);
                }
                else {
                    int qs = in.nextInt()-1, qe = in.nextInt()-1;
                    out.printf("%d\n", tree.querySum(n, qs, qe));
                }
            }
        }
        out.close();
    }
}
