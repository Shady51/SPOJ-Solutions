import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.InputStream;
import java.util.StringTokenizer;

class Main {
    static class Reader
    {
        private BufferedReader br;
        private StringTokenizer token;
        protected Reader(InputStream obj)
        {
            br = new BufferedReader(new InputStreamReader(obj));
            token = null;
        }
        protected String next()
        {
            while (token == null || !token.hasMoreTokens())
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
    }
    public static int solve (String str) {
        int n = str.length();
        int[][] D = new int[3][n];
        /*
        * i     0 1 2 3 4 5 6 7 8 ...
        * K    0_|_|_|_|_|_|_|_|_ ...
        * KE   1_|_|_|_|_|_|_|_|_ ...
        * KEK  2_|_|_|_|_|_|_|_|_ ...
        * */
        if (str.charAt(0) == 'K') {
            D[0][0] = 1;
        }
        for (int i=1; i<n; i++) {
            if (str.charAt(i) == 'K') {
                D[0][i] = D[0][i-1]+1;
                D[1][i] = D[1][i-1];
                D[2][i] = D[1][i-1] + D[2][i-1];
            }
            else if (str.charAt(i) == 'E') {
                D[0][i] = D[0][i-1];
                D[1][i] = D[0][i-1] + D[1][i-1];
                D[2][i] = D[2][i-1];
            }
            else {
                D[0][i] = D[0][i-1];
                D[1][i] = D[1][i-1];
                D[2][i] = D[2][i-1];
            }
        }
        return D[2][n-1];
    }
    public static void main(String[] args) throws IOException {
        Reader in = new Reader(System.in);
        PrintWriter out = new PrintWriter(System.out);
        int T = in.nextInt();
        while (T-->0) {
            String str = in.nextLine();
            out.printf("%d\n", solve(str));
        }
        out.close();
    }
}
