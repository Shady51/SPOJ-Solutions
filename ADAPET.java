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

        protected Reader(InputStream stream) {
            br = new BufferedReader(new InputStreamReader(stream), 32768);
            token = null;
        }
        protected String next() {
            while (token == null || !token.hasMoreElements()) {
                try {
                    token = new StringTokenizer(br.readLine());
                } catch (IOException e) { e.printStackTrace(); }
            }
            return token.nextToken();
        }
        protected int nextInt() {return Integer.parseInt(next());}
    }
    public static int[] LPS(String str, int str_len) {
        int j=0, i=1;
        int[] lps = new int[str_len];
        while (i<str_len) {
            if (str.charAt(i) == str.charAt(j)) {
                lps[i] = j+1;
                i++; j++;
            }
            else if (j != 0) j = lps[j-1];
            else lps[i++] = j;
        }
        return (lps);
    }
    public static void main (String[] args) throws IOException {
        Reader in = new Reader(System.in);
        PrintWriter out = new PrintWriter(System.out);
        int t = in.nextInt();
        while (t-->0) {
            String str = in.next(); int k = in.nextInt(), str_len = str.length();
            out.printf("%d\n", (long)str_len + (k-1)*(long)(str_len - LPS(str, str_len)[str_len-1]));
        }
        out.close();
    }
}
