import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.StringTokenizer;
import java.util.Arrays;

class Main{
    static class Reader
    {
        private BufferedReader br;
        private StringTokenizer token;
        public Reader(InputStream obj) {
            br = new BufferedReader(new InputStreamReader(obj));
            token = null;
        }
        public String next()
        {
            while (token == null || !token.hasMoreElements())
            {
                try
                {
                    token = new StringTokenizer(br.readLine());
                }
                catch (IOException e)
                {
                    e.printStackTrace();
                }

            }
            return token.nextToken();
        }
        public int[] nextIntArr(int n)
        {
            int[] arr = new int[n];
            for(int i=0; i<n; i++){
                arr[i] = nextInt();
            }
            return arr;
        }
        public int nextInt(){return Integer.parseInt(next());}
    }
    public static int longest_altenating_subsequence(int[] arr) {
        if (arr.length == 1) return 1;
        if (arr.length == 2) {
            if (Math.abs(arr[0])<Math.abs(arr[1]) && arr[1]/arr[0]<0) return 2;
            return 1;
        }
        int[] max = new int[arr.length];
        Arrays.fill(max, 1);
        int ans = 1;
        for (int i=1; i<arr.length; i++){
            if (arr[i]>0){
                for (int j=0; j<i; j++){
                    if (arr[j]<0 && (max[j]+1)>max[i] && Math.abs(arr[j])<Math.abs(arr[i]))
                        max[i] = max[j]+1;
                }
            }
            else {
                for (int j=0; j<i; j++){
                    if (arr[j]>0 && (max[j]+1)>max[i] && Math.abs(arr[j])<Math.abs(arr[i]))
                        max[i] = max[j]+1;
                }
            }
            if(max[i]>ans) ans = max[i];
        }
        return ans;
    }

    public static void main(String[] args) throws IOException {
        Reader in = new Reader(System.in);
        PrintWriter out = new PrintWriter(System.out);
        out.printf("%d\n", longest_altenating_subsequence(in.nextIntArr(in.nextInt())));
        out.close();
    }
}
