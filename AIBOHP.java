import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.StringTokenizer;

class Main {
    static class Reader {
        private BufferedReader br;
        private StringTokenizer stkz;
        public Reader()
        {
            br = new BufferedReader(new InputStreamReader(System.in));
            stkz = null;
        }
        public String next()
        {
            while (stkz == null || !stkz.hasMoreElements())
            {
                try
                {
                    stkz = new StringTokenizer(br.readLine());
                }
                catch (IOException e)
                {
                    e.printStackTrace();
                }
            }
            return stkz.nextToken();

        }

        public int nextInt(){return Integer.parseInt(next());}

        public String nextLine()
        {
            String str = "";
            try
            {
                str = br.readLine();
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
            return str;
        }

    }

    public static int LCS_length(String s1, String s2) {
        int[][] LCS = new int[s1.length()+1][s2.length()+1];
        for (int i=1; i<=s1.length(); i++){
            for (int j=1; j<=s2.length(); j++) {
                if (s1.charAt(i-1) == s2.charAt(j-1)){
                    LCS[i][j] = 1 + LCS[i-1][j-1];
                }
                else {
                    LCS[i][j] = Math.max(LCS[i-1][j], LCS[i][j-1]);
                }
            }
        }
        return LCS[s1.length()][s2.length()];
    }

    public static void main(String[] args) throws IOException {
        Reader in = new Reader();
        PrintWriter out = new PrintWriter(System.out);
        int t = in.nextInt();
        while (t-->0) {
            String str = in.nextLine();
            StringBuilder sb = new StringBuilder(str);
            sb.reverse();
            out.printf("%d\n", (str.length() - LCS_length(str, sb.toString())));
        }
        out.close();
    }
}
