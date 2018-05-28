import java.io.*;
import java.util.*;

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
            } return token.nextToken();
        }
        protected int nextInt() {return Integer.parseInt(next());}
        protected long nextLong() {return Long.parseLong(next());}
        protected double nextDouble() {return Double.parseDouble(next());}
    }

    public static void main(String[] args) throws IOException {
        Reader in = new Reader(System.in);
        PrintWriter out = new PrintWriter(System.out);
        int n = in.nextInt();
        while (n-->0) {
            int m = in.nextInt(), k =in.nextInt(); long low = 0, high = 0;
            int[] arr = new int[m];
            for (int i =0; i<m; i++) {
                arr[i] = in.nextInt();
                low = Math.max(low, arr[i]);
                high += arr[i];

            }
            ArrayList<String> ans = new ArrayList<>();
            while (low<high) {
                long mid = low + (high-low)/2;
                long req = 1, load = arr[0];
                for (int i=1; i<m; i++) {
                    if (load+arr[i] <= mid) load += arr[i];
                    else {
                        req++; load = arr[i];
                    }
                }
                if (req <= k) high = mid;
                else low = mid+1;
            }
            int used = 1; long cs = arr[arr.length-1]; ans.add(String.valueOf(arr[arr.length-1]));
            for (int i=arr.length-2; i>=0; i--) {
                if (cs + arr[i] <= low && i+1 > k-used) {
                    cs += arr[i];
                    ans.add(String.valueOf(arr[i]));
                } else {
                    ans.add("/"); ans.add(String.valueOf(arr[i]));
                    used++;
                    cs = arr[i];
                }
            }
            Collections.reverse(ans);
            for (String x: ans) out.printf("%s ", x);
            out.printf("\n");
        }
        out.close();
    }
}
