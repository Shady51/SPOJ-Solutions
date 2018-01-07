import java.io.*;
import java.util.*;

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

        public int nextInt(){return Integer.parseInt(next());}
    }


    public static void main(String[] args) throws IOException {
        Reader in = new Reader();
        PrintWriter out = new PrintWriter(System.out);
        int n;
        ArrayList<Integer> al1 = new ArrayList<Integer>();
        ArrayList<Integer> al2 = new ArrayList<Integer>();
        ArrayList<Integer> nodes = new ArrayList<Integer>();

        while ((n = in.nextInt()) != 0) {
            al1.clear(); al2.clear(); nodes.clear();
            for (int i=0; i<n; i++) al1.add(in.nextInt());
            n = in.nextInt();
            for (int i=0; i<n; i++) {
                int t = in.nextInt();
                al2.add(t);
                if (Collections.binarySearch(al1, t) >= 0) nodes.add(t);
            }
            int[] arr1 = new int[nodes.size()+1];
            int[] arr2 = new int[nodes.size()+1];
            int ind = 0;
            for (int x: al1) {
                if (ind == nodes.size()) arr1[ind] += x;
                else {
                    if (x == nodes.get(ind)) ind++;
                    else arr1[ind] += x;
                }
            }
            ind = 0;
            for (int x: al2) {
                if (ind == nodes.size()) arr2[ind] += x;
                else {
                    if (x == nodes.get(ind)) ind++;
                    else arr2[ind] += x;
                }
            }
            long ans = 0;
            for (int i=0; i<arr1.length; i++) ans += Math.max(arr1[i], arr2[i]);
            for (int x: nodes) ans += x;
            out.printf("%d\n", ans);
        }
        out.close();
    }
}
