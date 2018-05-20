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

    public static void main(String[] args) throws IOException {
        Reader in = new Reader(System.in);
        PrintWriter out = new PrintWriter(System.out);
        int n = in.nextInt(), m = in.nextInt(); int[] arr = new int[n];
        for (int i=0; i<n; i++) arr[i] = in.nextInt();
        long ans = 0, sum = 0; int l = 0, r = 0;
        while (l<n) {
            while (r<n && sum + arr[r] <= m) sum += arr[r++];
            ans = Math.max(ans, sum);
            sum -= arr[l++];
        } out.printf("%d\n", ans);
        out.close();
    }
}
