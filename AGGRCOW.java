import java.io.*;
import java.util.Arrays;
import java.util.StringTokenizer;

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
        protected int[] nextIntArr(int n) {
            int[] arr = new int[n];
            for (int i=0; i<n; i++) arr[i] = nextInt();
            return arr;
        }
    }

    public static void main (String[] args) throws IOException {
        PrintWriter out = new PrintWriter(System.out);
        Reader in = new Reader(System.in);
        int t = in.nextInt();
        while (t-->0) {
            int n = in.nextInt(), c = in.nextInt();
            int[] arr = in.nextIntArr(n);
            Arrays.sort(arr);
            int low = 1, high = 1_000_000_000;
            while (low<high) {
                int mid = low + (high-low+1)/2;
                int req = 1, pos = 0;
                for (int i=1; i<n; i++) {
                    if (arr[i]-arr[pos] >= mid) {
                        req++; pos = i;
                    }
                }
                if (req<c) high = mid-1;
                else low = mid;
            }
            out.printf("%d\n", low);
        }
        out.close();
    }
}
