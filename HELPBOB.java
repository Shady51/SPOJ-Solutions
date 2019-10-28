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
import java.util.Formatter;
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
        HelpBob solver = new HelpBob();
        solver.solve(1, in, out);
        out.close();
    }

    static class HelpBob {
        private double[][] mat;
        private double[] price;
        private double[] area;
        private double[] totalAreaOfMask;
        private double[][] totalDiscountOfMask;

        private double totalDiscount(int mask, int to) {
            double ans = 1;
            int pizzaNo = 0;
            while (mask > 0) {
                if ((mask & (1)) != 0) {
                    ans *= (1.0 - mat[pizzaNo][to]);
                }
                pizzaNo++;
                mask >>= 1;
            }
            return ans;
        }

        private double totalArea(int mask) {
            double ans = 0;
            int pizzaNo = 0;
            while (mask > 0) {
                if ((mask & (1)) != 0) {
                    ans += area[pizzaNo];
                }
                pizzaNo++;
                mask >>= 1;
            }
            return ans;
        }

        public void solve(int testNumber, InputReader in, OutputWriter out) {
            int n;
            while (true) {
                n = in.nextInt();
                if (n == 0) break;

                mat = new double[n][n];
                price = new double[n];
                area = new double[n];
                totalAreaOfMask = new double[1 << n];
                totalDiscountOfMask = new double[1 << n][n];
                double[][] dp = new double[(1 << n)][n];
                for (int i = 0; i < dp.length; i++) Arrays.fill(dp[i], Double.MAX_VALUE);
                for (int i = 0; i < n; i++) {
                    double p = in.nextDouble(), a = in.nextDouble();
                    price[i] = p;
                    area[i] = a;
                    int coupons = in.nextInt();
                    for (int coupon = 0; coupon < coupons; coupon++) {
                        int to = in.nextInt() - 1;
                        double discountRate = in.nextDouble() / 100;
                        mat[i][to] = discountRate;
                    }
                }
                int allMasked = (1 << n) - 1;
                for (int mask = 1; mask <= allMasked; mask++) {
                    totalAreaOfMask[mask] = totalArea(mask);
                }
                for (int mask = 1; mask <= allMasked; mask++) {
                    for (int to = 0; to < n; to++) {
                        if ((mask & (1 << to)) != 0) continue;
                        totalDiscountOfMask[mask][to] = totalDiscount(mask, to);
                    }
                }
                for (int i = 0; i < n; i++)
                    dp[1 << i][i] = price[i];

                for (int mask = 1; mask <= allMasked; mask++) {
                    for (int lastPizza = 0; lastPizza < n; lastPizza++) {
                        if ((mask & (1 << lastPizza)) == 0) continue;
                        for (int nextPiza = 0; nextPiza < n; nextPiza++) {
                            if ((mask & (1 << nextPiza)) != 0) continue;

                            double totalDis = totalDiscountOfMask[mask][nextPiza];
                            dp[(mask | (1 << nextPiza))][nextPiza] =
                                    Math.min(dp[(mask | (1 << nextPiza))][nextPiza], dp[mask][lastPizza] + price[nextPiza] * (totalDis));

                        }
                    }
                }
                double ans = Double.MAX_VALUE;
                for (int mask = 1; mask <= allMasked; mask++) {
                    for (int lastPizza = 0; lastPizza < n; lastPizza++) {
                        if ((mask & (1 << lastPizza)) == 0) continue;
                        ans = Math.min(ans, dp[mask][lastPizza] / totalAreaOfMask[mask]);

                    }

                }
                Formatter fmt = new Formatter();
                fmt.format("%.4f", ans);
                out.println(fmt);
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

        public double nextDouble() {
            int c = read();
            while (isSpaceChar(c)) {
                c = read();
            }
            int sgn = 1;
            if (c == '-') {
                sgn = -1;
                c = read();
            }
            double res = 0;
            while (!isSpaceChar(c) && c != '.') {
                if (c == 'e' || c == 'E') {
                    return res * Math.pow(10, nextInt());
                }
                if (c < '0' || c > '9') {
                    throw new InputMismatchException();
                }
                res *= 10;
                res += c - '0';
                c = read();
            }
            if (c == '.') {
                c = read();
                double m = 1;
                while (!isSpaceChar(c)) {
                    if (c == 'e' || c == 'E') {
                        return res * Math.pow(10, nextInt());
                    }
                    if (c < '0' || c > '9') {
                        throw new InputMismatchException();
                    }
                    m /= 10;
                    res += (c - '0') * m;
                    c = read();
                }
            }
            return res * sgn;
        }

        private boolean isSpaceChar(int c) {
            return c == ' ' || c == '\n' || c == '\r' || c == '\t' || c == -1;
        }

    }
}
