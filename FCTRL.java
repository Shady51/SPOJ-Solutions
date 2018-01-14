import java.lang.*;
import java.util.*;
import java.io.*;

class Main {
    static class Reader {
        private BufferedReader br;
        private StringTokenizer stkz;
        public Reader(InputStream obj) {
            br = new BufferedReader(new InputStreamReader(obj));
            stkz = null;
        }
        String next() {
            while (stkz == null || !stkz.hasMoreElements())
            {
                try
                {
                    stkz = new StringTokenizer(br.readLine());
                }
                catch (IOException  e)
                {
                    e.printStackTrace();
                }
            }
            return stkz.nextToken();
        }
        
        int nextInt() {return Integer.parseInt(next());}
        
    }
    public static void main(String[] args){
        Reader in = new Reader(System.in);
        PrintWriter out = new PrintWriter(System.out);
        int t = in.nextInt();
        while(t-->0) {
            int n = in.nextInt();
            int p =1, a=0;
            while(true) {
                a+=(n/Math.pow(5, p));
                p++;
                if(n/Math.pow(5, p) == 0) break;
            }
            out.printf("%d\n", a);
        }
        out.close();
    }
}
