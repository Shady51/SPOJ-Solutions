import java.io.IOException;
import java.io.PrintWriter;
import java.io.InputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.*;

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
            } return token.nextToken();
        }
        protected int nextInt() {return Integer.parseInt(next());}
        protected long nextLong() {return Long.parseLong(next());}
    }
    static int[] h; 
    static int[] c;
    private static long cost_func (int x, int n) {
        long ans = 0;
        for (int i=0; i<n; i++) ans += c[i]*Math.abs(h[i]-x);
        return ans;
    }
    public static void main(String[] args) throws IOException {
        Reader in = new Reader(System.in);
        PrintWriter out = new PrintWriter(System.out);
        int t = in.nextInt();
        while (t-->0) {
            int n = in.nextInt();
            h = new int[n]; c = new int[n];
            for (int i = 0; i < n; i++) h[i] = in.nextInt();
            for (int i = 0; i < n; i++) c[i] = in.nextInt();
            int low = 0, high = 10_000; long cost1, cost2;
            while (low<=high) {
                int mid1 = low + (high-low)/3, mid2 = high - (high-low)/3;
                if (mid1 == mid2) break;
                cost1 = cost_func(mid1, n); cost2 = cost_func(mid2, n);
                if (cost2<=cost1) low = mid1+1;
                else high = mid2-1;
            } out.printf("%d\n", cost_func(low, n));
        }
        out.close();
    }
}
