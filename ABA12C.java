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
        
        public int[] nextIntprice(int n){
            int[] price = new int[n];
            for(int i=0; i<n; i++){
                price[i] = nextInt();
            }
            return price;
        }
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
    static int m;
    static int[] D = new int[m];
    
    public static int solve(int[] price, int K) {
        for(int i=2; i<=K; i++){
            D[i] = price[i];
            for(int j=1; j<i; j++){
                if(price[i-j] == -1  || D[j] == -1)
                    continue;
                if(D[i] == -1)
                    D[i] = D[j] + price[i-j];
                else
                    D[i] = Math.min(D[i], D[j] + price[i-j]);
            }
        }
        return D[K];
    }
    
    public static void main(String[] args) throws IOException {
        Reader in = new Reader();
        PrintWriter out = new PrintWriter(System.out);
        int t = in.nextInt();
        while (t-->0){
            int N = in.nextInt();
            int K = in.nextInt();
            if (K == 0) out.printf("%d\n", 0);
            else {
                m = K + 1;
                D = new int[m];
                int[] price = new int[K + 1];
                price[0] = -1;
                for (int i = 1; i <= K; i++) {
                    price[i] = in.nextInt();
                }
                D[1] = price[1];
                out.printf("%d\n", solve(price, K));
            }
        }
        out.close();
    }
}
