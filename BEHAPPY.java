import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.InputStream;
import java.util.StringTokenizer;

class Main {
    static class Reader {
        private BufferedReader br;
        private StringTokenizer token;
        public Reader(InputStream obj)
        {
            br = new BufferedReader(new InputStreamReader(obj));
            token = null;
        }
        public String next()
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
        public int nextInt() {return Integer.parseInt(next());}
    }
    static int count = 0;
    public static void solve (int[][] arr, int sum, int m, int n, int gf, int rem) {
        if (gf == m-1 && rem>=arr[gf][0] && rem<=arr[gf][1]) {
            count++;
            return;
        }
        else if (gf == m-1) return;
        for (int i=arr[gf][0]; i<=arr[gf][1]; i++) solve(arr, sum+i, m, n, gf+1, rem-i);
    }
    public static void main(String[] args) throws IOException {
        Reader in = new Reader(System.in);
        PrintWriter out = new PrintWriter(System.out);
        int m;
        while ((m=in.nextInt()) !=0 ) {
            int n = in.nextInt();
            int[][] arr = new int[m][2];
            for (int i=0; i<m; i++) {
                arr[i][0] = in.nextInt();
                arr[i][1] = in.nextInt();
            }
            count = 0;
            solve(arr, 0, m, n, 0, n);
            out.printf("%d\n", count);
        }
        out.close();
    }
}
