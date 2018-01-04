import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.StringTokenizer;

class Main{
    static class Reader{
        private BufferedReader br;
        private StringTokenizer stkz;
        public Reader()
        {
            stkz = null;
            br = new BufferedReader(new InputStreamReader(System.in));
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
    static HashMap<Long, Long> hm = new HashMap<Long, Long>();
    public static long solve(long n){
        if(n == 0) return 0;
        if(hm.containsKey(n)) return hm.get(n);
        hm.put(n, Math.max(n, (solve(n/2)+solve(n/3)+solve(n/4))));
        return hm.get(n);
    }
    public static void main(String[] args) throws IOException{
        Reader in = new Reader();
        PrintWriter out = new PrintWriter(System.out);
        String s;
        while((s=in.nextLine())!=null){
            System.out.println(solve(Long.parseLong(s)));
        }
        out.close();
    }
}
