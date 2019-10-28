import java.io.OutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.PriorityQueue;
import java.io.BufferedWriter;
import java.util.InputMismatchException;
import java.io.IOException;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.AbstractCollection;
import java.io.Writer;
import java.io.OutputStreamWriter;
import java.util.Comparator;
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
        PaidRoads solver = new PaidRoads();
        solver.solve(1, in, out);
        out.close();
    }

    static class PaidRoads {
        private int n;
        private int m;
        private ArrayList<Node>[] graph = new ArrayList[10];

        public void solve(int testNumber, InputReader in, OutputWriter out) {
            n = in.nextInt();
            m = in.nextInt();
            for (int i = 0; i < n; i++) graph[i] = new ArrayList<>();
            for (int i = 0; i < m; i++) {
                int a = in.nextInt() - 1, b = in.nextInt() - 1, c = in.nextInt() - 1, p = in.nextInt(), r = in.nextInt();
                graph[a].add(new Node(b, c, p, r));
            }

            long[][] dp = new long[(1 << n)][n];
            for (int i = 0; i < (1 << n); i++) Arrays.fill(dp[i], Long.MAX_VALUE);
            dp[1][0] = 0;

            PriorityQueue<Triplet> pq = new PriorityQueue<>(Comparator.comparingLong(o -> o.disFromStart));
            pq.offer(new Triplet(1, 0, 0));

            while (!pq.isEmpty()) {
                int ver = pq.peek().lastCity;
                long disFromStart = pq.peek().disFromStart;
                int mask = pq.poll().mask;
                if (dp[mask][ver] < disFromStart) continue;
                for (Node node : graph[ver]) {
                    int b = node.b, c = node.c, p = node.p, r = node.r;
                    if ((mask & (1 << c)) != 0) {
                        if (dp[(mask | (1 << b))][b] > disFromStart + Math.min(p, r)) {
                            dp[(mask | (1 << b))][b] = disFromStart + Math.min(p, r);
                            pq.offer(new Triplet((mask | (1 << b)), b, dp[(mask | (1 << b))][b]));
                        }
                    } else {
                        if (dp[(mask | (1 << b))][b] > disFromStart + r) {
                            dp[(mask | (1 << b))][b] = disFromStart + r;
                            pq.offer(new Triplet((mask | (1 << b)), b, dp[(mask | (1 << b))][b]));
                        }
                    }
                }
            }

            long ans = Long.MAX_VALUE;
            for (int i = 1; i < (1 << n); i++) {
                if ((i & (1 << (n - 1))) == 0) continue;
                ans = Math.min(ans, dp[i][n - 1]);
            }
            if (ans == Long.MAX_VALUE) out.println("impossible");
            else out.println(ans);
            out.flush();
        }

        class Node {
            int b;
            int c;
            int p;
            int r;

            Node(int _b, int _c, int _p, int _r) {
                b = _b;
                c = _c;
                p = _p;
                r = _r;
            }

        }

        class Triplet {
            int mask;
            int lastCity;
            long disFromStart;

            Triplet(int mask, int lastCity, long disFromStart) {
                this.mask = mask;
                this.lastCity = lastCity;
                this.disFromStart = disFromStart;
            }

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
