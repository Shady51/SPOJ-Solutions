import java.io.IOException;
import java.io.PrintWriter;
import java.io.InputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

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
            }
            return token.nextToken();
        }
        protected int nextInt() {return Integer.parseInt(next());}
    }
    
    public static int max_arr (int[] arr) { // to calculate the maximum value in an array
        int max = arr[0];
        int n = arr.length;
        for (int i=1; i<n; i++) {
            if (arr[i]>max) max = arr[i];
        }
        return max;
    }
    public static int[] LDS_rl (int[] arr) { //finding the longest decreasing subsequence while traversing from right to left
        int n = arr.length;
        int[] lds = new int[n];
        Arrays.fill(lds, 1);
        for (int i=n-2; i>=0; i--) {
            for (int j=n-1; j>i; j--) {
                if (arr[i]>arr[j] && lds[i] < lds[j] + 1) lds[i] = lds[j]+1;
            }
        }
        return lds;
    }
    
    public static int LDS_lr (int[] arr) { //finding the longest decreasing subsequence while traversing from left to right
        int n = arr.length;
        int[] lds = new int[n];
        Arrays.fill(lds, 1);
        for (int i=1; i<n; i++) {
            for (int j=0; j<i; j++) {
                if (arr[i]<arr[j] && lds[i] < lds[j] + 1) lds[i] = lds[j] + 1;
            }
        }
        return lds[n-1]; // to return the length of LDS, ending at the last peak.
    }
    
    public static void main(String[] args) throws IOException {
        Reader in = new Reader(System.in);
        PrintWriter out = new PrintWriter(System.out);
        int t = in.nextInt();
        while (t-->0) {
            int n = in.nextInt(), m = in.nextInt();
            int[] arr = new int[n];
            for (int i=0; i<n; i++) arr[i] = in.nextInt();
            int d_lds = max_arr(LDS_rl(arr));
            if (n == 1) out.printf("%d\n", 1);
            else {
                if (m >= n - 1) {//mini-bat is useless
                    out.printf("%d\n", d_lds);
                }
                else {//may use mini-bat m: [0, n-2]
                    int ans_left = LDS_lr(Arrays.copyOfRange(arr, 0, m+1));
                    int ans_right = max_arr(LDS_rl(Arrays.copyOfRange(arr, m+1, arr.length)));
                    out.printf("%d\n", Math.max(d_lds, (ans_left+ans_right)));
                }
            }
        }
        out.close();
    }
}
