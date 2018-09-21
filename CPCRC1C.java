import java.util.*;
import java.lang.*;
import java.io.*;

class Main {
    private static Reader in;
    private static PrintWriter out;
    static class Reader {
        private BufferedReader br;
        private StringTokenizer token;
        protected Reader(FileReader obj) {
            br = new BufferedReader(obj, 32768);
            token = null;
        }
        protected Reader() {
            br = new BufferedReader(new InputStreamReader(System.in), 32768);
            token = null;
        }
        protected String next() {
            while(token == null || !token.hasMoreTokens()) {
                try {
                    token = new StringTokenizer(br.readLine());
                } catch (Exception e) {e.printStackTrace();}
            } return token.nextToken();
        }
        protected int nextInt() {return Integer.parseInt(next());}
        protected long nextLong() {return Long.parseLong(next());}
        protected double nextDouble() {return Double.parseDouble(next());}
    }
    static ArrayList<Integer> digit;
    static long[][][] dp;
    private static void setDigit(int num) {
        digit = new ArrayList<>();
        while (num > 0) {
            digit.add(num%10);
            num /= 10;
        }
    }
    private static long sumOfDigitsUTIL(int ind, int sum, int limit) {
        if (ind == -1) return sum;
        if (dp[ind][sum][limit] != -1 && limit == 0) return dp[ind][sum][limit];
        int m = (limit == 1) ? digit.get(ind) : 9;
        long ans = 0;
        for (int i=0; i<=m; i++) {
            ans += sumOfDigitsUTIL(ind-1, sum+i, (digit.get(ind) == i) ? limit : 0);
        }
        if (limit == 0) dp[ind][sum][limit] = ans;
        return ans;
    }
    private static long sumOfDigits(int num) {
        setDigit(num);
        return sumOfDigitsUTIL(digit.size()-1, 0, 1);
    }
    public static void main(String[] args) throws IOException {
        in = new Reader();
        out = new PrintWriter(System.out);
        int a, b;
        dp = new long[10][90][2];
        for (int i=0; i<10; i++) {
            for (int j=0; j<82; j++) {
                dp[i][j][0] = dp[i][j][1] = -1;
            }
        }
        while ((a=in.nextInt()) != -1 && (b = in.nextInt()) != -1) {
            out.printf("%d\n", sumOfDigits(b) - sumOfDigits(a-1));
        }
        out.close();
    }
}
