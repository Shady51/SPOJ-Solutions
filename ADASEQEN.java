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

    static int[][] DP;
    static int[] cost;

    public static int solve(String s1, String s2, int s1_len, int s2_len) {
        for (int i=1; i<=s1_len; i++) {
            for (int j=1; j<=s2_len; j++) {
                if (s1.charAt(i-1) == s2.charAt(j-1)) DP[i][j] = DP[i-1][j-1] + cost[(int)s1.charAt(i-1) - 97];
                else DP[i][j] = Math.max(DP[i-1][j], DP[i][j-1]);

            }
        }
        return DP[s1_len][s2_len];
    }

    public static void main(String[] args) throws IOException {
        Reader in = new Reader(System.in);
        PrintWriter out = new PrintWriter(System.out);
        int s1_len = in.nextInt(), s2_len = in.nextInt();
        cost = new int[26];
        for (int i=0; i<26; i++) cost[i] = in.nextInt();
        String s1 = in.next(), s2 = in.next();
        DP = new int[s1_len+1][s2_len+1];
        out.printf("%d\n", solve(s1, s2, s1_len, s2_len));
        out.close();
    }
}
