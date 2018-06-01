import java.util.*;
import java.lang.*;
import java.io.*;
import static java.lang.Math.*;

class Main {
    static class Reader {
        private BufferedReader br;
        private StringTokenizer token;
        protected Reader(InputStream obj) {
            br = new BufferedReader(new InputStreamReader(obj), 32768);
            token = null;
        }
        protected String next() {
            while (token == null || !token.hasMoreElements()) {
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
        Reader in = new Reader(System.in);
        PrintWriter out = new PrintWriter(System.out);
        int n = in.nextInt(); int[] arr = new int[n];
        for (int i=0; i<n; i++) arr[i] = in.nextInt();
        int[] dp = new int[n]; Arrays.fill(dp, 1);
        for (int i=1; i<n; i++) {
            for (int j=0; j<i; j++) {
                if (abs(arr[j]) < abs(arr[i]) && (((long)arr[i])*((long)arr[j])<0) && dp[j] + 1 > dp[i]) 
                    dp[i] = dp[j] + 1;
            }
        }
        int ans = 1; for (int x: dp) ans = max(ans, x);
        out.printf("%d\n", ans);
        out.close();
    }
}
