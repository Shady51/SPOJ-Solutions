import java.io.DataInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;

class ABC {
    static class Reader
    {
        final private int BUFFER_SIZE = 1<<26;
        private DataInputStream din;
        private byte[] buffer;
        private int bufferPointer, bytesRead;
        public Reader()
        {
            din = new DataInputStream(System.in);
            buffer = new byte[BUFFER_SIZE];
            bufferPointer = bytesRead = 0;
        }
        public String readLine() throws IOException
        {
            byte[] buf = new byte[1<<26];
            int cnt = 0, c;
            while ((c = read()) != -1)
            {
                if (c == '\n')
                    break;
                buf[cnt++] = (byte) c;
            }
            return new String(buf, 0, cnt);
        }
        public String next() throws IOException
        {
            byte[] buf = new byte[1<<26];
            int cnt = 0, c;
            while ((c = read()) != -1)
            {
                if (c == ' ' || c == '\n')
                    break;
                buf[cnt++] = (byte) c;
            }
            return new String(buf, 0, cnt);
        }
        public int[] nextIntArr(int n) throws IOException
        {
            int[] arr = new int[n];
            for(int i=0; i<n; i++){
                arr[i] = nextInt();
            }
            return arr;
        }
        public int nextInt() throws IOException
        {
            int ret = 0;
            byte c = read();
            while (c <= ' ')
                c = read();
            boolean neg = (c == '-');
            if (neg)
                c = read();
            do
            {
                ret = (ret<<3) + (ret<<1) + c - '0';
            }  while ((c = read()) >= '0' && c <= '9');

            if (neg)
                return -ret;
            return ret;
        }
        public long nextLong() throws IOException
        {
            long ret = 0;
            byte c = read();
            while (c <= ' ')
                c = read();
            boolean neg = (c == '-');
            if (neg)
                c = read();
            do {
                ret = (ret<<3) + (ret<<1) + c - '0';
            }
            while ((c = read()) >= '0' && c <= '9');
            if (neg)
                return -ret;
            return ret;
        }
        public double nextDouble() throws IOException
        {
            double ret = 0, div = 1;
            byte c = read();
            while (c <= ' ')
                c = read();
            boolean neg = (c == '-');
            if (neg)
                c = read();

            do {
                ret = ret * 10 + c - '0';
            }
            while ((c = read()) >= '0' && c <= '9');

            if (c == '.')
            {
                while ((c = read()) >= '0' && c <= '9')
                {
                    ret += (c - '0') / (div *= 10);
                }
            }

            if (neg)
                return -ret;
            return ret;
        }
        private void fillBuffer() throws IOException
        {
            bytesRead = din.read(buffer, bufferPointer = 0, BUFFER_SIZE);
            if (bytesRead == -1)
                buffer[0] = -1;
        }
        private byte read() throws IOException
        {
            if (bufferPointer == bytesRead)
                fillBuffer();
            return buffer[bufferPointer++];
        }
        public void close() throws IOException
        {
            if (din == null)
                return;
            din.close();
        }
    }
    
    public static int[] Length_LIS_Array (int[] arr) {
        int[] lis = new int[arr.length];
        Arrays.fill(lis, 1);
        for (int i=1; i<arr.length; i++) {
            for (int j=0; j<i; j++) {
                if (arr[i]>arr[j] && lis[j]+1 > lis[i]) {
                    lis[i] = lis[j]+1;
                }
            }
        }
        return lis;
    }
    
    public static int[] Length_LDS_Array (int[] arr) {
        int[] lds = new int[arr.length];
        Arrays.fill(lds, 1);
        for (int i=arr.length-2; i>=0; i--) {
            for (int j=arr.length-1; j>i; j--) {
                if (arr[i]>arr[j] && lds[j]+1 > lds[i]) {
                    lds[i] = lds[j]+1;
                }
            }
        }
        return lds;
    }

    public static void main(String[] args) throws IOException {
        Reader in = new Reader();
        PrintWriter out = new PrintWriter(System.out);
        int t = in.nextInt();
        while (t-->0)  {
            int N = in.nextInt();
            int[] arr = in.nextIntArr(N);
            int[] lis = Length_LIS_Array(arr);
            int[] lds = Length_LDS_Array(arr);
            int ans = lds[0]+lis[0]-1;
            for (int i=1; i<N; i++) {
                if (lds[i]+lis[i]-1 > ans) ans = lds[i]+lis[i]-1;
            }
            out.printf("%d\n", ans);
        }
        out.close();
    }
}
