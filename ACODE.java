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
            stkz = null;
            br = new BufferedReader(new InputStreamReader(System.in));
        }
        public String next()
        {
            while (stkz == null || !stkz.hasMoreElements())
            {
                try
                {
                    stkz = new StringTokenizer(br.readLine());
                }
                catch(IOException e)
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
            catch(IOException e)
            {
                e.printStackTrace();
            }
            return str;
        }
    }
    static long[] DP;
    public static boolean can_be_clubbed (char c1, char c2) {
        String str = ""+c1+c2;
        int temp = Integer.parseInt(str);
        if (temp>=1 && temp <= 26) return true;
        else return false;
    }
    public static long Decode (String str, int m) {
        for (int i=2; i<=m; i++) {
            if (str.charAt(i-1) == '0' && !can_be_clubbed(str.charAt(i-2), str.charAt(i-1))) return  0;
            if (str.charAt(i-1) == '0') DP[i] = DP[i-2];
            else if (str.charAt(i-2) == '0') DP[i] = DP[i-1];
            else if (can_be_clubbed(str.charAt(i-2), str.charAt(i-1))) {
                DP[i] = DP[i-1]+DP[i-2];
            }
            else DP[i] = DP[i-1];
        }
        return DP[m];
    }
    public static void main(String[] args) throws IOException {
        Reader in = new Reader();
        PrintWriter out = new PrintWriter(System.out);
        String str;
        while(!((str=in.nextLine()).equals("0"))) {
            int m = str.length();
            DP = new long[m+1];
            DP[0] = 1;
            DP[1] = 1;
            if (str.charAt(0) == '0') out.printf("%d\n", 0);
            else out.printf("%d\n", Decode(str, m));
        }
        out.close();
    }
}
