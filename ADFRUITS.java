import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.InputStream;

class Main {
    static class Reader {
        private BufferedReader br;
        
        protected Reader() {
            br = new BufferedReader(new InputStreamReader(System.in));
        }

        protected String nextLine() {
            String str = "";
            try {
                str = br.readLine();
            }
            catch (IOException e) {
                e.printStackTrace();
            }
            return str;
        }
    }
    public static String LCS (String s1, String s2, int s1_len, int s2_len) {
        int[][] lcs = new int[s1_len+1][s2_len+1];
        for (int i=1; i<=s1_len; i++) {
            for (int j=1; j<=s2_len; j++) {
                if (s1.charAt(i-1) == s2.charAt(j-1)) lcs[i][j] = 1 + lcs[i-1][j-1];
                else lcs[i][j] = Math.max(lcs[i-1][j], lcs[i][j-1]);
            }
        }
        int i = s1_len, j = s2_len;
        StringBuilder sb = new StringBuilder("");
        while (i!=0 && j!=0) {
            if (s1.charAt(i-1) == s2.charAt(j-1)) {
                sb.append(s1.charAt(i-1));
                i--; j--;
            }
            else {
                int t1 = lcs[i-1][j], t2 = lcs[i][j-1];
                if (t1>t2) i--;
                else if (t2>t1) j--;
                else {
                    if (i == Math.min(i, j)) i--;
                    else j--;
                }
            }
        }
        return sb.reverse().toString();
    }

    public static void main(String[] args) throws IOException {
        Reader in = new Reader();
        PrintWriter out = new PrintWriter(System.out);
        String s_in;
        while ((s_in = in.nextLine()) != null) {
            String s1, s2;
            int ind_of_space = s_in.indexOf(' ');
            s1 = s_in.substring(0, ind_of_space); s2 = s_in.substring(ind_of_space+1);
            int n = s1.length(), m = s2.length();
            String lcs = LCS(s1, s2, n, m);
            if (lcs.length()>=1) {
                int[][] lcs_marks = new int[2][lcs.length()];
                int i = 0, j=0;
                int lcs_1 = 0, lcs_2 = 0;
                while (i!=s1.length() && j!=s2.length() && lcs_1!=lcs.length() && lcs_2!=lcs.length()) {
                    if (s1.charAt(i) == lcs.charAt(lcs_1)) {
                        lcs_marks[0][lcs_1] = i;
                        lcs_1++;
                    }
                    if (s2.charAt(j) == lcs.charAt(lcs_2)) {
                        lcs_marks[1][lcs_2] = j;
                        lcs_2++;
                    }
                    i++; j++;
                }
                while (i!=s1.length() && lcs_1!=lcs.length()) {
                    if (s1.charAt(i) == lcs.charAt(lcs_1)) {
                        lcs_marks[0][lcs_1] = i;
                        lcs_1++;
                    }
                    i++;
                }
                while (j!=s2.length() && lcs_2!=lcs.length()) {
                    if (s2.charAt(j) == lcs.charAt(lcs_2)) {
                        lcs_marks[1][lcs_2] = j;
                        lcs_2++;
                    }
                    j++;
                }
                StringBuilder sb_ans = new StringBuilder();
                sb_ans.append(s1.substring(0, lcs_marks[0][0])).append(s2.substring(0, lcs_marks[1][0]));
                for (int z=0; z<lcs.length()-1; z++) {
                    sb_ans.append(s1.charAt(lcs_marks[0][z]));
                    sb_ans.append(s1.substring(lcs_marks[0][z]+1, lcs_marks[0][z+1]));
                    sb_ans.append(s2.substring(lcs_marks[1][z]+1, lcs_marks[1][z+1]));
                }
                sb_ans.append(s1.charAt(lcs_marks[0][lcs.length()-1]));
                sb_ans.append(s1.substring(lcs_marks[0][lcs.length()-1]+1));
                sb_ans.append(s2.substring(lcs_marks[1][lcs.length()-1]+1));
                out.printf("%s\n", sb_ans.toString());
            }
            else out.printf("%s\n", ""+s1+s2);
        }
        out.close();
    }
}
