import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.StringTokenizer;

class Main {
    static class Reader {
        private BufferedReader br;
        private StringTokenizer stkz;

        protected Reader() {
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
    static int[][] D;
    public static int solve (int[][] arr, int h, int w) {
        for (int i=1; i<h; i++) {
            for (int j=0; j<w; j++) {
                if (j==0) {
                    D[i][j] = Math.max(D[i-1][j], D[i-1][j+1]) + arr[i][j];
                }
                else if (j==(w-1)) {
                    D[i][j] = Math.max(D[i-1][j], D[i-1][j-1]) + arr[i][j];
                }
                else {
                    D[i][j] = Math.max(Math.max(D[i-1][j-1], D[i-1][j+1]), D[i-1][j]) + arr[i][j];
                }
            }
        }
        int max = 0;
        for (int j=0; j<w; j++) {
            if (max < D[h-1][j]) max = D[h-1][j];
        }
        return max;
    }
    public static void main(String[] args) throws IOException {
        Reader in = new Reader();
        PrintWriter out = new PrintWriter(System.out);
        int T = in.nextInt();
        while (T-->0) {
            int h = in.nextInt(), w = in.nextInt();
            int[][] arr = new int[h][w];
            D = new int[h][w];
            for (int i=0; i<h; i++) {
                for (int j=0; j<w; j++) {
                    arr[i][j] = in.nextInt();
                }
            }
            for (int j=0; j<w; j++) {
                D[0][j] = arr[0][j];
            }
            out.printf("%d\n", solve(arr, h, w));
        }
        out.close();
    }
}
