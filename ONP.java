import java.io.DataInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Stack;

class Anon{
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
            byte[] buf = new byte[1<<26]; // line length
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
            byte[] buf = new byte[1<<26]; // string length
            int cnt = 0, c;
            while ((c = read()) != -1)
            {
                if (c == ' ' || c == '\n')
                    break;
                buf[cnt++] = (byte) c;
            }
            return new String(buf, 0, cnt);
        }

        public long[] nextLongArr(int n) throws IOException
        {
            long[] arr = new long[n];
            for(int i=0; i<n; i++){
                arr[i] = nextLong();
            }
            return arr;
        }
        public int[] nextIntArr(int n) throws IOException
        {
            int[] arr = new int[n];
            for(int i=0; i<n; i++){
                arr[i] = nextInt();
            }
            return arr;
        }
        public Integer[] nextIntegerArray(int n) throws IOException
        {
            Integer[] arr = new Integer[n];
            for(int i=0; i<n; i++){
                arr[i] = nextInt();
            }
            return arr;
        }
        public Long[] nextLongArray(int n) throws IOException
        {
            Long[] arr = new Long[n];
            for(int i=0; i<n; i++){
                arr[i] = nextLong();
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
    
    public static int opPrecedence(char c) {
        switch (c) {
            case '^':
                return 3;
            case '*':
                return 2;
            case '/':
                return 2;
            case '+':
                return 1;
            case '-':
                return 1;
            default:
                return -1;
        }
    }
    
    public static void main(String[] args) throws IOException {
        Reader in = new Reader();
        PrintWriter out = new PrintWriter(System.out);
        int t = in.nextInt();
        while(t-->0) {
            String s = in.readLine();
            char[] s_arr = s.toCharArray();
            Stack<Character> st = new Stack<Character>();
            StringBuilder sb = new StringBuilder("");
            for (char ch: s_arr) {
                if (Character.isLetter(ch)) {
                    sb.append(ch);
                    continue;
                }
                if (ch == '(') st.push(ch);
                else if (ch == ')') {
                    while (!st.isEmpty() && st.peek() != '(') {
                        sb.append(st.pop());
                    }
                    st.pop();
                }
                else if (opPrecedence(ch) > opPrecedence(st.peek())) st.push(ch);
                else {
                    while (st.peek() != '(' && !st.isEmpty() && opPrecedence(ch)<=opPrecedence(st.peek())) {
                        sb.append(st.pop());
                    }
                    st.push(ch);
                }
            }
            while (!st.isEmpty()) sb.append(st.pop());
            out.printf("%s\n", sb.toString());
        }
        out.close();
    }
}
