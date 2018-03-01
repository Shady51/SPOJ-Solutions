import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.InputStream;
import java.util.Arrays;
import java.util.StringTokenizer;
class BIT {
    private int[] BIT;
    private long inv = 0;
    protected BIT(int[] arr, int n) {
        BIT = new int[n+1];
        arr = convert(arr, n);
        for (int i=n-1; i>=0; i--) {
            inv += getSum(arr[i]-1);
            update(n, arr[i], 1);
        }
    }
    private long getSum (int index) {
        long sum = 0;
        while (index>0) {
            sum += BIT[index];
            index -= index & (-index);
        } return sum;
    }
    private void update (int n, int index, int val) {
        while (index<=n) {
            BIT[index] += val;
            index += index & (-index);
        }
    }
    private static int lower_bound (int[] arr, int from, int to, int val) {
        int low = from, high = to;
        while (low<high) {
            final int mid = (low+high)/2;
            if (val<=arr[mid]) high = mid;
            else low = mid+1;
        }
        return low;
    }
    private int[] convert (int[] arr, int n) {
        int[] temp = new int[n];
        System.arraycopy(arr, 0, temp, 0, n);
        Arrays.sort(temp);
        for (int i=0; i<n; i++) arr[i] = lower_bound(temp, 0, n, arr[i]) + 1;
        return arr;
    }
    protected long getInvCount () {
        return inv;
    }
}
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

    public static void main (String[] args) throws IOException {
        Reader in = new Reader(System.in);
        PrintWriter out = new PrintWriter(System.out);
        int t = in.nextInt();
        while (t-->0) {
            int n = in.nextInt(); int[] arr = new int[n];
            for (int i=0; i<n; i++) arr[i] = in.nextInt();
            BIT obj = new BIT(arr, n);
            out.printf("%d\n", obj.getInvCount());
        }
        out.close();
    }
}
