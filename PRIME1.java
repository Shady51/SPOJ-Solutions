import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.Arrays;
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
        public long[] nextLongArr(int n)
        {
            long[] arr = new long[n];
            for(int i=0; i<n; i++){
                arr[i] = nextLong();
            }
            return arr;
        }
        public int[] nextIntArr(int n)
        {
            int[] arr = new int[n];
            for(int i=0; i<n; i++){
                arr[i] = nextInt();
            }
            return arr;
        }
        public Integer[] nextIntegerArray(int n)
        {
            Integer[] arr = new Integer[n];
            for(int i=0; i<n; i++){
                arr[i] = nextInt();
            }
            return arr;
        }
        public Long[] nextLongArray(int n)
        {
            Long[] arr = new Long[n];
            for(int i=0; i<n; i++){
                arr[i] = nextLong();
            }
            return arr;
        }
        public int nextInt()
        {
            return Integer.parseInt(next());
        }
        public long nextLong()
        {
            return Long.parseLong(next());
        }
        public double nextDouble()
        {
            return Double.parseDouble(next());
        }
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
    public static boolean isPrime(int n){
        if(n == 1) return false;
        for(int i=2; i<=Math.floor(Math.sqrt(n)); i++){
            if(n%i == 0) return false;
        }
        return true;
    }
    public static void main(String[] args) throws IOException{
        Reader in = new Reader();
        PrintWriter out = new PrintWriter(System.out);
        int t = in.nextInt();
        while(t-->0){
            int m = in.nextInt(), n = in.nextInt();
            for(int i=m; i<=n; i++){
                if (isPrime(i)) out.printf("%d\n", i);
            }
            out.println();
        }
        out.close();
    }
}
