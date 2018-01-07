import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.StringTokenizer;

class Main {
    static class Reader {
        private BufferedReader br;
        private StringTokenizer stkz;

        protected Reader()
        {
            br = new BufferedReader(new InputStreamReader(System.in));
            stkz = null;
        }
        protected String next ()
        {
            while (stkz == null || !stkz.hasMoreTokens())
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
        protected String nextLine()
        {
            String str = "";
            try {
                str = br.readLine();
            }
            catch (IOException e){
                e.printStackTrace();
            }
            return str;
        }
        protected int nextInt() {return Integer.parseInt(next());}
        protected long nextLong() {return Long.parseLong(next());}
        protected int[] nextIntArr(int n) {
            int[] arr = new int[n];
            for (int i=0; i<n; i++) {
                arr[i] = nextInt();
            }
            return arr;
        }
    }
    static final double prob = 1.0/6.0;
    static double[][] D = new double[3010][3010];

    public static void main(String[] args) throws IOException {
        Reader in = new Reader();
        PrintWriter out = new PrintWriter(System.out);
        D[0][0] = 1;
        D[1][1] = prob;
        D[1][2] = prob;
        D[1][3] = prob;
        D[1][4] = prob;
        D[1][5] = prob;
        D[1][6] = prob;
        for (int i = 2; i<=550; i++) {
            for (int j = 1; j<=2000; j++) {
                for (int m=1; m<=6; m++) {
                    if ((j - m) >= 0) D[i][j] += D[i-1][j-m];
                }
                D[i][j] *= prob;
            }
        }
        int T = in.nextInt();
        while (T-->0) {
            int n = in.nextInt(), k = in.nextInt();
            if (k<n || k>6*n) out.printf("%d\n", 0);
            else if (n>3000 || k>3000) out.printf("%d\n", 0);
            else out.printf("%d\n", (int)(100*D[n][k]));
        }
        out.close();
    }
}
