import java.io.*;
import java.util.*;

class Main {
    static class Reader
    {
        private BufferedReader br;
        private StringTokenizer stkz;

        public Reader(InputStream ob)
        {
            br = new BufferedReader(new InputStreamReader(ob));
            stkz = null;
        }

        public String next()
        {
            while (stkz == null || !stkz.hasMoreElements())
            {
                try
                {
                    stkz = new StringTokenizer(br.readLine());
                }
                catch (IOException e)
                {
                    e.printStackTrace();
                }

            }
            return stkz.nextToken();
        }

        public String nextLine()
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

        public int[] nextIntArr(int n)
        {
            int[] arr = new int[n];
            for(int i=0; i<n; i++){
                arr[i] = nextInt();
            }
            return arr;
        }

        public int nextInt(){return Integer.parseInt(next());}
        public long nextLong(){return Long.parseLong(next());}
    }

    public static long getSum (long[] BITree, int ind) {
        int BIT_ind = ind+1;
        long sum = 0;
        while (BIT_ind>0) {
            sum += BITree[BIT_ind];
            BIT_ind = BIT_ind&(BIT_ind-1);
        }
        return sum;
    }
    
    public static long sum (int x, long[] BIT1, long[] BIT2) {
        return x*getSum(BIT1, x) - getSum(BIT2, x);
    }
    
    public static long rangeSum (int l, int r, long[] BIT1, long[] BIT2) {
        return sum (r, BIT1, BIT2) - sum(l-1, BIT1, BIT2);
    }
    
    public static void updateBIT (long[] BIT, int n, int ind, long val) {
        int BIT_ind = ind+1;
        while (BIT_ind<=n) {
            BIT[BIT_ind] += val;
            BIT_ind += BIT_ind&(-BIT_ind);
        }
    }
    
    public static void updateRange (long[] BIT1, long[] BIT2, int n, long val, int l, int r) {

        updateBIT(BIT1, n, l, val);
        updateBIT(BIT1, n, r+1, -val);

        updateBIT(BIT2, n, l, val*(l-1));
        updateBIT(BIT2, n, r+1, -val*r);
    }
    
    public static void main(String[] args) throws Exception {
        Reader in = new Reader(System.in);
        PrintWriter out = new PrintWriter(System.out);
        int t = in.nextInt();
        while (t-->0) {
            int n = in.nextInt(), c = in.nextInt();
            long[] BIT1 = new long[n+1];
            long[] BIT2 = new long[n+1];
            while (c-->0) {
                int type = in.nextInt();
                if (type == 0) {
                    int l = in.nextInt()-1, r = in.nextInt()-1;
                    long val = in.nextInt();
                    updateRange(BIT1, BIT2, n, val, l, r);
                }
                else out.printf("%d\n", rangeSum(in.nextInt()-1, in.nextInt()-1, BIT1, BIT2));
            }
        }
        out.close();
    }
}
