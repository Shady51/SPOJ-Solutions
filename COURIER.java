import java.io.OutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.Arrays;
import java.io.BufferedWriter;
import java.util.InputMismatchException;
import java.io.IOException;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Objects;
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
        TheCourier solver = new TheCourier();
        solver.solve(1, in, out);
        out.close();
    }

    static class TheCourier {
        private int n;
        private int m;
        private int home;
        private long[][] dp;
        private long[][] dis;

        private void FloydWarshall() {
            for (int k = 1; k <= n; k++) {
                for (int i = 1; i <= n; i++) {
                    for (int j = 1; j <= n; j++) {
                        if (dis[i][k] < Long.MAX_VALUE && dis[k][j] < Long.MAX_VALUE)
                            dis[i][j] = Math.min(dis[i][j], dis[i][k] + dis[k][j]);
                    }
                }
            }
        }

        public void solve(int testNumber, InputReader in, OutputWriter out) {
            int t = in.nextInt();
            while (t-- > 0) {
                n = in.nextInt();
                m = in.nextInt();
                home = in.nextInt();
                dis = new long[n + 1][n + 1];
                for (int i = 0; i <= n; i++) Arrays.fill(dis[i], Long.MAX_VALUE);
                for (int i = 1; i <= n; i++) dis[i][i] = 0;
                for (int i = 0; i < m; i++) {
                    int u = in.nextInt(), v = in.nextInt(), w = in.nextInt();
                    dis[u][v] = dis[v][u] = w;
                }
                int z = in.nextInt();
                ArrayList<Util.Pair<Integer>> al = new ArrayList<>();
                for (int i = 0; i < z; i++) {
                    int u = in.nextInt(), v = in.nextInt(), cnt = in.nextInt();
                    for (int k = 0; k < cnt; k++) al.add(new Util.Pair<>(u, v));
                }
                int masks = al.size();

                int allMasked = (1 << masks) - 1;
            /*
            dp[i][j] means that we have completed the errands whose bit is set in i
            and the last errand completed is j
             */
                dp = new long[(1 << masks)][masks];
                for (int i = 0; i < dp.length; i++) Arrays.fill(dp[i], Long.MAX_VALUE);

                FloydWarshall();
                //For every first errand:
                for (int firstMask = 0; firstMask < masks; firstMask++) {
                    dp[1 << firstMask][firstMask] = dis[home][al.get(firstMask).x] + dis[al.get(firstMask).x][al.get(firstMask).y];
                }

                for (int mask = 1; mask <= allMasked; mask++) {
                    for (int last = 0; last < masks; last++) {
                        if ((mask & (1 << last)) == 0) continue;
                        for (int next = 0; next < masks; next++) {
                            if ((mask & (1 << next)) != 0) continue;

                            dp[mask | (1 << next)][next] = Math.min(dp[mask | (1 << next)][next], dp[mask][last] + dis[al.get(last).y][al.get(next).x] + dis[al.get(next).x][al.get(next).y]);
                        }
                    }
                }
                //Find the best returning route:
                long ans = Long.MAX_VALUE;
                for (int lastMask = 0; lastMask < masks; lastMask++) {
                    ans = Math.min(ans, dp[allMasked][lastMask] + dis[al.get(lastMask).y][home]);
                }
                out.println(ans);
            }

            out.flush();
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
        public static class Pair<T> {
            public T x;
            public T y;

            public Pair(T x, T y) {
                this.x = x;
                this.y = y;
            }

            public boolean equals(Object obj) {
                if (!(obj instanceof Util.Pair)) return false;
                Util.Pair<T> pair = (Util.Pair<T>) obj;
                return this.x.equals(pair.x) && this.y.equals(pair.y);
            }

            public String toString() {
                return ("(" + this.x + "," + this.y + ")");
            }

            public int hashCode() {
                return Objects.hash(x, y);
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
}
