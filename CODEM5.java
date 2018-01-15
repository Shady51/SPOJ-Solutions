import java.io.IOException;
import java.io.PrintWriter;
import java.io.InputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

class Main {
    static class Reader {
        private BufferedReader br;
        private StringTokenizer token;

        protected Reader(InputStream obj) {
            br = new BufferedReader(new InputStreamReader(obj));
            token = null;
        }
        protected String next() {
            while (token == null || !token.hasMoreElements()) {
                try {
                    token = new StringTokenizer(br.readLine());
                } catch (IOException e) {e.printStackTrace();}
            }
            return token.nextToken();
        }
        protected int nextInt() {return Integer.parseInt(next());}
    }
    
    static long[][] DP;
    
    public static long solve (int[] arr, int ind, int sum) {
        if (sum == 0) return 0;
        if ((sum != 0 && ind<0) || (sum<0)) return Integer.MAX_VALUE;
        if (DP[ind][sum] == 0) DP[ind][sum] = Math.min(1+solve(arr, ind-1, sum-arr[ind]), solve(arr, ind-1, sum));
        return DP[ind][sum];
    }
    
    public static void main(String[] args) throws IOException {
        Reader in = new Reader(System.in);
        PrintWriter out = new PrintWriter(System.out);
        int t = in.nextInt();
        while (t-->0) {
            int n = in.nextInt(), k = in.nextInt();
            int[] arr = new int[n];
            for (int i=0; i<n; i++) arr[i] = in.nextInt();
            DP = new long[n][k+1];
            solve(arr, n-1, k);
            if (DP[n-1][k] != Integer.MAX_VALUE) out.printf("%d\n", DP[n-1][k]);
            else out.printf("%s\n", "impossible");
        }
        out.close();
    }
}
