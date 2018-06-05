import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.IOException;
import java.util.StringTokenizer;

class Matrix {
    public static long[][] multiply(long[][] a, long[][] b, long mod) {
        int n = a.length, m = a[0].length, k = b[0].length;
        long[][] res = new long[n][k];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < k; j++) {
                for (int p = 0; p < m; p++) {
                    res[i][j] = (res[i][j] + (a[i][p]*b[p][j])%mod)%mod;
                }
            }
        } return res;
    }
    public static long[][] pow(long[][] a, long n, long mod) {
        if (n == 0) return identity(a.length);
        if (n == 1) return a;
        else {
            if ((n&1) == 0) return pow(multiply(a, a, mod), n/2, mod);
            else return multiply(a, pow(multiply(a, a, mod), (n-1)/2, mod), mod);
        }
    }
    public static long[][] identity(int n) {
        long[][] res = new long[n][n];
        for (int i=0; i<n; i++) res[i][i] = 1;
        return res;
    }
}

class Main {
    static class Reader {
        private BufferedReader br;
        private StringTokenizer token;
        protected Reader (InputStream obj) {
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
    }

    public static void main (String[] args) throws IOException {
        PrintWriter out = new PrintWriter(System.out);
        Reader in = new Reader(System.in);
        int t = in.nextInt();
        while (t-->0) {
            int m = in.nextInt(), n = in.nextInt();
            long[][] arr = new long[m][m];
            for (int i=0; i<m; i++)
                for (int j=0; j<m; j++) arr[i][j] = in.nextLong();
            long[][] res = Matrix.pow(arr, n, 1000000007);
            for (int i=0; i<res.length; i++) {
                for (int j=0; j<res[0].length; j++) {
                    out.printf("%d ", res[i][j]);
                } out.printf("\n");
            }
        }
        out.close();
    }
}
