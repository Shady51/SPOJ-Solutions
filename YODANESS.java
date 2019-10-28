import java.io.OutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.BufferedWriter;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.io.IOException;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.io.Writer;
import java.io.OutputStreamWriter;
import java.io.InputStream;

/**
 * Built using CHelper plug-in
 * Actual solution is at the top
 */
public class Main {
    public static void main(String[] args) {
        InputStream inputStream = System.in;
        OutputStream outputStream = System.out;
        InputReader in = new InputReader(inputStream);
        OutputWriter out = new OutputWriter(outputStream);
        YodanessLevel solver = new YodanessLevel();
        solver.solve(1, in, out);
        out.close();
    }

    static class YodanessLevel {
        public void solve(int testNumber, InputReader in, OutputWriter out) {
            int t = in.nextInt();
            while (t-- > 0) {
                int n = in.nextInt(); //number of words
                HashMap<String, Integer> hm = new HashMap<>();
                String[] said = new String[n];
                for (int i = 0; i < n; i++) said[i] = in.next();
                String[] order = new String[n];
                for (int i = 0; i < n; i++) {
                    order[i] = in.next();
                    hm.put(order[i], i);
                }
                int[] arr = new int[n];
                for (int i = 0; i < n; i++) {
                    arr[i] = hm.get(said[i]);
                }
                SegmentTree st = new SegmentTree(n);
                int ans = 0;
                for (int i = 0; i < n; i++) {
                    int num = arr[i];
                    ans += st.query(0, 0, n - 1, num + 1, n - 1);
                    st.update(0, 0, n - 1, num, 1);
                }
                out.println(ans);
            }
            out.flush();
        }

        class SegmentTree {
            private int[] st;

            private SegmentTree(int n) {
                int x = (int) Math.ceil(Util.log2(n));
                st = new int[(1 << (x + 1)) - 1];
            }

            private void update(int sti, int sts, int ste, int ind, int val) {
                if (sts == ste) {
                    st[sti] = val;
                    return;
                }
                int mid = Util.getMid(sts, ste);
                if (ind <= mid) update(2 * sti + 1, sts, mid, ind, val);
                else update(2 * sti + 2, mid + 1, ste, ind, val);
                st[sti] = st[2 * sti + 1] + st[2 * sti + 2];
            }

            private int query(int sti, int sts, int ste, int qs, int qe) {
                if (qs > ste || qe < sts) return 0;
                if (qs <= sts && ste <= qe) return st[sti];
                int mid = Util.getMid(sts, ste);
                return query(2 * sti + 1, sts, mid, qs, qe) + query(2 * sti + 2, mid + 1, ste, qs, qe);
            }

        }

    }

    static class OutputWriter {
        private final PrintWriter writer;
        private ArrayList<String> res = new ArrayList<>();
        private StringBuilder sb = new StringBuilder("");

        public OutputWriter(OutputStream outputStream) {
            writer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(outputStream)));
        }

        public OutputWriter(Writer writer) {
            this.writer = new PrintWriter(writer);
        }

        public void println(Object... objects) {
            for (int i = 0; i < objects.length; i++) {
                sb.append(objects[i]);
            }
            res.add(sb.toString());
            sb = new StringBuilder("");
        }

        public void close() {
            writer.close();
        }

        public void flush() {
            for (String str : res) writer.printf("%s\n", str);
            res.clear();
            sb = new StringBuilder("");
        }

    }

    static class Util {
        public static int getMid(int a, int b) {
            return (a + b) >> 1;
        }

        public static double log2(int num) {
            return Math.log(num) / Math.log(2);
        }

    }

    static class InputReader {
        private InputStream stream;
        private byte[] buf = new byte[8192];
        private int curChar;
        private int numChars;

        public InputReader(InputStream stream) {
            this.stream = stream;
        }

        public InputReader(FileInputStream file) {
            this.stream = file;
        }

        public int read() {
            if (numChars == -1) {
                throw new InputMismatchException();
            }
            if (curChar >= numChars) {
                curChar = 0;
                try {
                    numChars = stream.read(buf);
                } catch (IOException e) {
                    throw new InputMismatchException();
                }
                if (numChars <= 0) {
                    return -1;
                }
            }
            return buf[curChar++];
        }

        public int nextInt() {
            int c = read();
            while (isSpaceChar(c)) {
                c = read();
            }
            int sgn = 1;
            if (c == '-') {
                sgn = -1;
                c = read();
            }
            int res = 0;
            do {
                if (c < '0' || c > '9') {
                    throw new InputMismatchException();
                }
                res = (res << 3) + (res << 1) + c - '0';
                c = read();
            } while (!isSpaceChar(c));
            return res * sgn;
        }

        public String next() {
            int c = read();
            while (isSpaceChar(c)) {
                c = read();
            }
            StringBuilder res = new StringBuilder();
            do {
                res.appendCodePoint(c);
                c = read();
            } while (!isSpaceChar(c));
            return res.toString();
        }

        private boolean isSpaceChar(int c) {
            return c == ' ' || c == '\n' || c == '\r' || c == '\t' || c == -1;
        }

    }
}
