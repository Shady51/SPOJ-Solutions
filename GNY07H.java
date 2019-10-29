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
        TilingAGridWithDominoes solver = new TilingAGridWithDominoes();
        solver.solve(1, in, out);
        out.close();
    }

    static class TilingAGridWithDominoes {
        private ArrayList<Integer>[] al = new ArrayList[16];
        private int n;
        private int num = 0;
        private int[][] dp;
        private boolean[][] vis;

        private void generateMasks(int mask, int i) {
            if (i == 4) {
                al[mask].add(num);
                return;
            }
            if ((mask & (1 << i)) != 0) generateMasks(mask, i + 1);
            else {
                num = num | (1 << i);
                generateMasks(mask, i + 1);
                num = num ^ (1 << i);
                if (i < 3 && (mask & (1 << i + 1)) == 0) {
                    generateMasks(mask, i + 2);
                }
            }
        }

        private int solveDP(int i, int mask) {
            if (vis[i][mask]) return dp[i][mask];
            if (i == n - 1) {
                vis[i][mask] = true;
                if (mask == 0 || mask == 3 || mask == 9 || mask == 12 || mask == 15) return dp[i][mask] = 1;
                else return dp[i][mask] = 0;
            }
            for (int k = 0; k < al[mask].size(); k++) {
                dp[i][mask] += solveDP(i + 1, al[mask].get(k));
            }
            vis[i][mask] = true;
            return dp[i][mask];
        }

        public void solve(int testNumber, InputReader in, OutputWriter out) {
            for (int i = 0; i < 16; i++) al[i] = new ArrayList<>();
            for (int i = 0; i < 16; i++) {
                num = 0;
                generateMasks(i, 0);
            }

            int t = in.nextInt();
            for (int test = 1; test <= t; test++) {
                n = in.nextInt();
                dp = new int[n][16];
                vis = new boolean[n][16];

                solveDP(0, 0);
                int ans = 0;
                for (int i = 0; i < 16; i++) ans += dp[0][i];
                out.println(test, ' ', ans);
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

        private boolean isSpaceChar(int c) {
            return c == ' ' || c == '\n' || c == '\r' || c == '\t' || c == -1;
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
