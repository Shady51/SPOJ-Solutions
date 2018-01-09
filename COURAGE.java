import java.io.*;
import java.util.*;
import java.lang.*;

class SegmentTree_Min {
    private int[] st;
    protected SegmentTree_Min (int[] arr, int n) {
        int x = (int)Math.ceil(Math.log(n)/Math.log(2));
        int max_size = 2*(int)Math.pow(2, x) - 1;
        st = new int[max_size];
        constructSegTree (arr, 0, n-1, 0);
    }

    private int constructSegTree (int[] arr, int sts, int ste, int sti) {
        if (sts == ste) {
            st[sti] = arr[sts];
            return arr[sts];
        }
        else {
            int mid = (sts+ste)/2;
            st[sti] = Math.min(constructSegTree(arr, sts, mid, 2*sti+1), constructSegTree(arr, mid+1, ste, 2*sti+2));
            return st[sti];
        }
    }
    protected void update (int n, int ind, int val) {
        updateUTIL (0, n-1, 0, ind, val);
    }
    private int updateUTIL(int sts, int ste, int sti, int ind, int val) {
        if (ind<sts || ind > ste) return st[sti];
        if (sts == ste) {
            st[sti] += val;
            return st[sti];
        }
        else {
            int mid = (sts+ste)/2;
            st[sti] = Math.min(updateUTIL(sts, mid, 2*sti+1, ind, val), updateUTIL(mid+1, ste, 2*sti+2, ind, val));
            return st[sti];
        }
    }

    protected int getMin (int n, int l, int r) {
        return getMinUTIL(0, n-1, 0, l, r);
    }
    private int getMinUTIL (int sts, int ste, int sti, int l, int r) {
        if (l>ste || sts>r) return Integer.MAX_VALUE;
        else if (sts == ste) {
            return st[sti];
        }
        else if (l<=sts && r>=ste) {
            return st[sti];
        }
        else {
            int mid = (sts + ste)/2;
            return Math.min(getMinUTIL(sts, mid, 2*sti+1, l, r), getMinUTIL(mid+1, ste, 2*sti+2, l, r));
        }
    }

}

class SegmentTree_Sum {
    private long[] st;
    protected SegmentTree_Sum(int[] arr, int n) {
        int x = (int) (Math.ceil(Math.log(n) / Math.log(2)));
        int max_size = 2*(int)Math.pow(2, x) - 1;
        st = new long[max_size];
        constructSegTree (arr, 0, n-1, 0);
    }

    private long constructSegTree (int[] arr, int sts, int ste, int sti) {
        if (sts == ste) {
            st[sti] = arr[sts];
            return arr[sts];
        }
        int mid  = (sts+ste)/2;
        st[sti] = constructSegTree (arr,  sts,  mid, 2*sti+1) + constructSegTree(arr, mid+1, ste, 2*sti+2);
        return st[sti];
    }
    private long getSumUTIL (int sts, int ste, int l, int r, int sti) {
        if (r<sts || ste <l) return 0;
        else if (l<=sts && r>=ste) return st[sti];
        else {
            int mid = (sts+ste)/2;
            return getSumUTIL(sts, mid, l, r, 2*sti+1) + getSumUTIL(mid+1, ste, l, r, 2*sti+2);
        }
    }
    protected long getSum (int n, int l, int r) {
        return getSumUTIL(0, n-1, l, r,  0);
    }
    private void updateUTIL (int sts, int ste, int sti, int ind, int val) {
        if (ind < sts || ind > ste) return;
        st[sti] += val;
        if (sts != ste) {
            int mid = (sts + ste) / 2;
            updateUTIL(sts, mid, 2 * sti+1, ind, val);
            updateUTIL(mid + 1, ste, 2 * sti + 2, ind, val);
        }
    }
    protected void update (int n, int ind, int val) {
        updateUTIL(0, n-1, 0, ind, val);
    }
}

class Main {
    static class Reader
    {
        private BufferedReader br;
        private StringTokenizer token;
        protected Reader(InputStream obj)
        {
            br = new BufferedReader(new InputStreamReader(obj));
            token = null;
        }
        protected String next()
        {
            while (token == null || !token.hasMoreElements())
            {
                try
                {
                    token = new StringTokenizer(br.readLine());
                }
                catch (IOException e)
                {
                    e.printStackTrace();
                }
            }
            return token.nextToken();
        }
        protected long[] nextLongArr(int n)
        {
            long[] arr = new long[n];
            for(int i=0; i<n; i++){
                arr[i] = nextLong();
            }
            return arr;
        }
        protected int[] nextIntArr(int n)
        {
            int[] arr = new int[n];
            for(int i=0; i<n; i++){
                arr[i] = nextInt();
            }
            return arr;
        }
        protected Integer[] nextIntegerArray(int n)
        {
            Integer[] arr = new Integer[n];
            for(int i=0; i<n; i++){
                arr[i] = nextInt();
            }
            return arr;
        }
        protected Long[] nextLongArray(int n)
        {
            Long[] arr = new Long[n];
            for(int i=0; i<n; i++){
                arr[i] = nextLong();
            }
            return arr;
        }
        protected int[][] nextIntMap (int rows, int columns) {
            int[][] arr = new int[rows][columns];
            for (int i=0; i<rows; i++) {
                for (int j=0; j<columns; j++) {
                    arr[i][j] = nextInt();
                }
            }
            return arr;
        }
        protected long[][] nextLongMap (int rows, int columns) {
            long[][] arr = new long[rows][columns];
            for (int i=0; i<rows; i++) {
                for (int j=0; j<columns; j++) {
                    arr[i][j] = nextLong();
                }
            }
            return arr;
        }
        protected int nextInt() {return Integer.parseInt(next());}
        protected long nextLong() {return Long.parseLong(next());}
        protected double nextDouble() {return Double.parseDouble(next());}

        protected String nextLine()
        {
            String str = "";
            try
            {
                str = br.readLine();
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
            return str;
        }
    }

    public static void main (String[] args) throws IOException {
        Reader in = new Reader(System.in);
        PrintWriter out = new PrintWriter(System.out);
        int n = in.nextInt();
        int[] arr = in.nextIntArr(n);
        //Use two segment trees to calculate the minimum of values in one, and sum in the other.
        //Or use a single segment tree of pair of values, one for sum, and the other for min of values.
        SegmentTree_Min seg_min = new SegmentTree_Min(arr, n);
        SegmentTree_Sum seg_sum = new SegmentTree_Sum(arr, n);
        int p = in.nextInt();
        while (p-->0) {
            String type = in.next();
            if (type.equals("COUNT")) {
                int l = in.nextInt(), r = in.nextInt();
                out.printf("%d\n", seg_sum.getSum(n, l, r)-seg_min.getMin(n, l, r));
            }
            else if (type.equals("EAT")) {
                int x = in.nextInt(), y = in.nextInt();
                seg_sum.update(n, y, -x);
                seg_min.update(n, y, -x);
            }
            else if (type.equals("GROW")) {
                int x = in.nextInt(), y = in.nextInt();
                seg_sum.update(n, y, x);
                seg_min.update(n, y, x);
            }
        }
        out.close();
    }
}
