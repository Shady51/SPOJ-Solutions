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
        public Reader(InputStream obj) {
            br = new BufferedReader(new InputStreamReader(obj));
            token = null;
        }
        public String next() {
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

    static int[][] LCS;
    static int[][][] DP;

    public static int LCS (String s1, String s2, int s1_len, int s2_len) {
        LCS = new int[s1_len+1][s2_len+1];
        for (int i=1; i<=s1_len; i++) {
            for (int j=1; j<=s2_len; j++) {
                if (s1.charAt(i-1) == s2.charAt(j-1)) {
                    LCS[i][j] = 1 + LCS[i-1][j-1];
                }
                else {
                    LCS[i][j] = Math.max(LCS[i-1][j], LCS[i][j-1]);
                }
            }
        }
        return LCS[s1_len][s2_len];
    }


    public static int solve(String s1, String s2, int i, int j, int l) {
        if(l==0) return 0;
        if(i<0 || j<0) return Integer.MIN_VALUE;
        if (DP[i][j][l] != 0) return DP[i][j][l];
        if(s1.charAt(i)==s2.charAt(j))
            DP[i][j][l] = Math.max(solve(s1,s2,i-1,j-1,l) ,solve(s1,s2,i-1,j-1,l-1)+(int)(s1.charAt(i)));
        else
            DP[i][j][l] = Math.max(solve(s1,s2,i,j-1,l), solve(s1,s2,i-1,j,l));
        return DP[i][j][l];
    }

    public static void main(String[] args) throws IOException {
        Reader in = new Reader(System.in);
        PrintWriter out = new PrintWriter(System.out);
        int t = in.nextInt();
        while (t-->0) {
            String s1 = in.next(), s2 = in.next();
            int k = in.nextInt(), s1_len = s1.length(), s2_len = s2.length(), len_LCS = LCS(s1, s2, s1_len, s2_len);
            if (len_LCS>=k) {
                DP = new int[s1_len+1][s2_len+1][k+1];
                out.printf("%d\n", solve(s1, s2, s1_len-1, s2_len-1, k));
            }
            else out.printf("%d\n", 0);
        }
        out.close();
    }
}
