import java.io.OutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.BufferedWriter;
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
        StrangeBillboard solver = new StrangeBillboard();
        solver.solve(1, in, out);
        out.close();
    }

    static class StrangeBillboard {
        public void solve(int testNumber, InputReader in, OutputWriter out) {
            int n = in.nextInt(), m = in.nextInt();
            int[] arr;
            int[] state;

            while (n + m != 0) {
                arr = new int[n];
                state = new int[n];
                for (int i = 0; i < n; i++) {
                    String str = in.nextLine();
                    int num = 0;
                    for (int j = 0; j < m; j++) {
                        if (str.charAt(j) == 'X') num |= (1 << j);
                    }
                    arr[i] = num;
                }
                int allMasked = (1 << m) - 1;
                long ans = Long.MAX_VALUE;
                for (int mask = 0; mask <= allMasked; mask++) {
                    System.arraycopy(arr, 0, state, 0, n);
                    int cnt = Integer.bitCount(mask);
                    for (int j = 0; j < m; j++) {
                        if ((mask & (1 << j)) != 0) {
                            state[0] = state[0] ^ (1 << (j));
                            if (1 <= n - 1) state[1] = state[1] ^ (1 << j);
                            if (j < m - 1) state[0] ^= (1 << (j + 1));
                            if (j > 0) state[0] ^= (1 << (j - 1));
                        }
                    }
                    for (int row = 1; row < n; row++) {
                        for (int j = 0; j < m; j++) {
                            if (((state[row - 1]) & (1 << j)) != 0) {
                                cnt++;
                                state[row] = state[row] ^ (1 << (j));
                                if (row < n - 1) state[row + 1] = state[row + 1] ^ (1 << (j));
                                if (j < m - 1) state[row] ^= (1 << (j + 1));
                                if (j > 0) state[row] ^= (1 << (j - 1));
                            }
                        }
                    }

                    if (state[n - 1] == 0) ans = Math.min(ans, cnt);
                }

                if (ans != Long.MAX_VALUE) out.println("You have to tap ", ans, " tiles.");
                else out.println("Damaged billboard.");
                n = in.nextInt();
                m = in.nextInt();
            }

            out.flush();
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

        public String nextLine() {
            int c = read();
            while (isSpaceChar(c))
                c = read();
            StringBuilder res = new StringBuilder();
            do {
                res.appendCodePoint(c);
                c = read();
            } while (!isEndOfLine(c));
            return res.toString();
        }

        private boolean isSpaceChar(int c) {
            return c == ' ' || c == '\n' || c == '\r' || c == '\t' || c == -1;
        }

        private boolean isEndOfLine(int c) {
            return c == '\n' || c == '\r' || c == -1;
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
}
