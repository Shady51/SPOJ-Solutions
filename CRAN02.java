import java.io.*;
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
        protected double nextDouble() {return Double.parseDouble(next());}
    }
    private static long nCr(int n, int r) {
        long numer = 1, denom = 1;
        if (0 <= r && r <= n) {
            int lim = Math.min(r, n - r);
            for (int x=1; x<=lim; x++) {
                numer *= n;
                denom *= x;
                n -= 1;
            }
            return numer/denom;
        } else return 0;
    }
    public static void main(String[] args) throws IOException {
        Reader in = new Reader(System.in);
        PrintWriter out = new PrintWriter(System.out);
        int t = in.nextInt();
        while (t-->0) {
            long ans = 0; HashMap<Integer, Integer> hm = new HashMap<>();
            int n = in.nextInt();
            int[] arr = new int[n]; int[] pre = new int[n];
            arr[0] = in.nextInt(); pre[0] = arr[0]; hm.put(pre[0], 1);
            for (int i=1; i<n; i++) {
                arr[i] = in.nextInt();
                pre[i] = pre[i-1] + arr[i];
                hm.compute(pre[i], (k, v) -> (v == null) ? 1 : v+1);
            }
            for (Map.Entry<Integer, Integer> entry: hm.entrySet()) ans += nCr(entry.getValue(), 2);
            try {
                ans += hm.get(0);
            } catch (Exception e) {}
            out.printf("%d\n", ans);
        }
        out.close();
    }
}
